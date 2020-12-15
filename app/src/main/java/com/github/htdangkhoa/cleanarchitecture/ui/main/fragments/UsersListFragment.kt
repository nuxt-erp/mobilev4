package com.github.htdangkhoa.cleanarchitecture.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseFragment
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.UsersResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.adapters.UsersListAdapter
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainViewModel
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_main.progressCircular
import kotlinx.android.synthetic.main.users_list_fragment.*

class UsersListFragment() : BaseFragment<MainViewModel>(MainViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.users_list_fragment

    private val alphabeticalComparator: Comparator<UsersResponse.User> =
        Comparator { a, b -> a.id.compareTo(b.id) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun render(view: View, savedInstanceState: Bundle?) {
        val mAdapter = UsersListAdapter(
            this.context,
            viewModel,
            alphabeticalComparator
        )
        val linearLayoutManager = LinearLayoutManager(this.context)

        userList.layoutManager = linearLayoutManager
        userList.adapter = mAdapter

        viewModel.resourceUsers.observe(viewLifecycleOwner, object : ObserverResource<Array<UsersResponse.User>>() {
            override fun onSuccess(data: Array<UsersResponse.User>) {
                mAdapter.add(data)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let { toast(it) }
                    handleHttpError(it)
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }

        })
        viewModel.getUsers()

    }
}