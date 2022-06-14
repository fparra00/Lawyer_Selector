package com.example.lawyerselectorv2.payments


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.PrincipalMenuActivity
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.ShowContactDatesLawyer
import com.synap.pay.SynapPayButton
import com.synap.pay.handler.EventHandler
import com.synap.pay.handler.payment.SynapAuthorizeHandler
import com.synap.pay.model.payment.*
import com.synap.pay.model.payment.response.SynapAuthorizeResponse
import com.synap.pay.model.security.SynapAuthenticator
import com.synap.pay.theming.SynapDarkTheme
import com.synap.pay.theming.SynapLightTheme
import com.synap.pay.theming.SynapTheme
import kotlinx.android.synthetic.main.activity_lawyer_user.*
import kotlinx.android.synthetic.main.activity_payment_credit.*
import kotlinx.android.synthetic.main.activity_payment_credit.lawName4
import kotlinx.android.synthetic.main.activity_payment_credit.lyCreditCard
import kotlinx.android.synthetic.main.activity_payment_credit.lyViewCard
import java.security.MessageDigest


class PaymentCreditActivity : AppCompatActivity() {

    //Aux Vars
    private lateinit var paymentWidget: SynapPayButton
    private var synapForm: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_credit)
        //Init Vars
        synapForm = cdsynapFormR
        cdsynapFormR.foregroundGravity=Gravity.CENTER
        //Aux Functions
        startPayment()

        //..onClicks..
        lyCreditCard.setOnClickListener {
            showLy(lyViewCard,lyCreditCard)
        }
        lyBank.setOnClickListener {
            showLy(lyViewBank,lyBank)
        }
        lyPayPal.setOnClickListener {
            showLy(lyViewPayPal, lyPayPal)
        }
        synapButtonR.setOnClickListener {
            paymentWidget.pay()
        }

    }

    private fun startPayment() {
        paymentWidget = SynapPayButton.create(synapForm)

        val theme: SynapTheme = SynapDarkTheme()
        SynapPayButton.setTheme(theme)
        SynapPayButton.setEnvironment(SynapPayButton.Environment.SANDBOX)
        val transaction: SynapTransaction = this.buildTransaction()!!
        val authenticator: SynapAuthenticator = this.buildAuthenticator(transaction)!!

        SynapPayButton.setListener(object : EventHandler {
            override fun onEvent(event: SynapPayButton.Events?) {
                val paymentButton: Button
                when (event) {
                    SynapPayButton.Events.START_PAY -> {
                        paymentButton = findViewById(R.id.synapButtonR)
                        paymentButton.setVisibility(View.GONE)
                    }
                    SynapPayButton.Events.INVALID_CARD_FORM -> {
                        paymentButton = findViewById(R.id.synapButtonR)
                        paymentButton.setVisibility(View.VISIBLE)
                    }
                    SynapPayButton.Events.VALID_CARD_FORM -> {}
                    else -> {}
                }
            }
        })
        paymentWidget.configure(
            authenticator,
            transaction,
            object : SynapAuthorizeHandler {
                override fun success(response: SynapAuthorizeResponse) {
                    Looper.prepare()
                    val resultSuccess = response.success
                    if (resultSuccess) {
                        val resultAccepted = response.result.accepted
                        val resultMessage = response.result.message
                        if (resultAccepted) {
                            showMessage(resultMessage)
                        } else {
                            showMessage(resultMessage)
                        }
                    } else {
                        val messageText = response.message.text
                        showMessage(messageText)
                    }
                    Looper.loop()
                }

                override fun failed(response: SynapAuthorizeResponse) {
                    goToShowContactDates()
                }
            }
        )
        val paymentButton: Button
        paymentButton = findViewById(R.id.synapButtonR)
        paymentButton.setVisibility(View.VISIBLE)
    }

    private fun buildTransaction(): SynapTransaction? {
        val number = System.currentTimeMillis().toString()
        val country = SynapCountry()
        country.code = "PER"
        val currency = SynapCurrency()
        currency.code = "PEN"
        val amount = "1.00"
        val customer = SynapPerson()
        customer.name = "Javier"
        customer.lastName = "Pérez"
        val address = SynapAddress()
        address.country = "PER"
        address.levels = ArrayList()
        address.levels.add("150000")
        address.levels.add("150100")
        address.levels.add("150101")
        address.line1 = "Av La Solidaridad 103"
        address.zip = "15034"
        customer.address = address
        customer.email = "javier.perez@synapsis.pe"
        customer.phone = "999888777"
        val document = SynapDocument()
        document.type =
            "DNI"
        document.number = "44556677"
        customer.document = document
        val productItem = SynapProduct()
        productItem.code = "123"
        productItem.name = "Llavero"
        productItem.quantity = "1"
        productItem.unitAmount = "1.00"
        productItem.amount = "1.00"
        val products: MutableList<SynapProduct> = ArrayList()
        products.add(productItem)
        val metadataItem = SynapMetadata()
        metadataItem.name = "name1"
        metadataItem.value = "value1"
        val metadataList: MutableList<SynapMetadata> = ArrayList()
        metadataList.add(metadataItem)
        val order = SynapOrder()
        order.number = number
        order.amount = amount
        order.country = country
        order.currency = currency
        order.products = products
        order.customer = customer
        order.shipping = customer
        order.billing = customer
        order.metadata = metadataList

        val features = SynapFeatures()
        val cardStorage = SynapCardStorage()
        cardStorage.userIdentifier =
            "javier.perez@synapsis.pe"
        features.cardStorage = cardStorage

        val settings = SynapSettings()
        settings.brands = listOf("VISA", "MSCD", "AMEX", "DINC")
        settings.language = "es_PE"
        settings.businessService = "MOB"

        val transaction = SynapTransaction()
        transaction.order = order
        transaction.features = features
        transaction.settings = settings
        return transaction
    }

    private fun buildAuthenticator(transaction: SynapTransaction): SynapAuthenticator? {
        val apiKey = "ab254a10-ddc2-4d84-8f31-d3fab9d49520"
        val signatureKey = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo"
        val signature: String = generateSignature(transaction, apiKey, signatureKey)
        val authenticator = SynapAuthenticator()
        authenticator.identifier = apiKey
        authenticator.signature = signature
        return authenticator
    }

    private fun showMessage(message: String) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage(message)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, id ->
                val looper = Handler(applicationContext.mainLooper)
                looper.post(Runnable {
                    synapForm!!.visibility = View.GONE
                })
                dialog.cancel()
            }
        )
        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }


    private fun generateSignature(
        transaction: SynapTransaction,
        apiKey: String,
        signatureKey: String
    ): String {
        val orderNumber = transaction.order.number
        val currencyCode = transaction.order.currency.code
        val amount = transaction.order.amount
        val rawSignature =
            apiKey + orderNumber + currencyCode + amount + signatureKey
        return sha512Hex(rawSignature)
    }

    private fun sha512Hex(value: String): String {
        val sb = StringBuilder()
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            val bytes: ByteArray = md.digest(value.toByteArray(charset("UTF-8")))
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i] + 0xff) + 0x100, 16).substring(1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    private fun showLy(ly: LinearLayout, cly: LinearLayout) {
        var set: AnimationSet = AnimationSet(true)
        var animation: Animation
        if (ly.visibility == View.GONE) {
            ly.visibility = View.VISIBLE
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                -0.2f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        } else {
            ly.visibility = View.GONE
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        }
        animation.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }

    // .. goTo..
    private fun goToShowContactDates() {
        val intent: Intent = Intent(this, ShowContactDatesLawyer::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }




}