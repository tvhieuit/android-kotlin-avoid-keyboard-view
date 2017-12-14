
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import general.rx.keyboard.RxKeyboardUtil
import io.reactivex.rxkotlin.subscribeBy

/**
 * ____________________________________
 *
 * Author: Hieu.TV - tvhieuit@gmail.com
 * Created: 12/14/17
 * ____________________________________
 */

class AvoidKeyboardRelativeLayout @JvmOverloads constructor(context : Context,
                                                            attrs : AttributeSet? = null,
                                                            defStyleAttr : Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		RxKeyboardUtil().create(this)
			.bindToLifecycle(this)
			.subscribeBy{
				this.setPadding(paddingLeft, paddingTop , paddingRight, it)
			}
	}

}