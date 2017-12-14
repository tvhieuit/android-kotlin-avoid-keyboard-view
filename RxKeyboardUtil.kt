package general.rx.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import io.reactivex.Observable

/**
 * ____________________________________
 *
 * Author: Hieu.TV - tvhieuit@gmail.com
 * Created: 12/14/17
 * ____________________________________
 */
class RxKeyboardUtil {

	fun create(rootView : View) = Observable.create<Int> {

		// why are we using a global layout listener? Surely Android
		// has callback for when the keyboard is open or closed? Surely
		// Android at least lets you query the status of the keyboard?
		// Nope! https://stackoverflow.com/questions/4745988/
		val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {

			val rect = Rect().apply { rootView.getWindowVisibleDisplayFrame(this) }

			val screenHeight = rootView.height

			// rect.bottom is the position above soft keypad or device button.
			// if keypad is shown, the rect.bottom is smaller than that before.
			val keypadHeight = screenHeight - rect.bottom

			// 0.15 ratio is perhaps enough to determine keypad height.
			if (keypadHeight > screenHeight * 0.15) {
				it.onNext(keypadHeight)
			} else {
				it.onNext(0)
			}
		}

		rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

		it.setCancellable {
			rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
		}
	}.distinctUntilChanged()!!
}