package dev.base.expand.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.able.IDevBaseViewBinding
import dev.base.fragment.DevBaseFragment
import dev.base.utils.ViewBindingUtils

/**
 * detail: Fragment ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseViewBindingFragment<VB : ViewBinding> : DevBaseFragment(),
    IDevBaseViewBinding<VB> {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (isViewBinding()) {
            if (isTryViewBindingCatch()) {
                try {
                    // ViewBinding 初始化处理
                    _binding = viewBinding(inflater, container)
                } catch (e: Exception) {
                    assist.printLog(e, "onCreateView - viewBinding")
                }
            } else {
                // ViewBinding 初始化处理
                _binding = viewBinding(inflater, container)
            }
        }
        return mContentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isDetachBinding()) _binding = null
    }

    // =======================
    // = IDevBaseViewBinding =
    // =======================

    final override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return ViewBindingUtils.viewBindingJavaClass(
            inflater,
            container,
            getBindingView(),
            javaClass
        )
    }

    final override fun getBindingView(): View? {
        return mContentView
    }
}