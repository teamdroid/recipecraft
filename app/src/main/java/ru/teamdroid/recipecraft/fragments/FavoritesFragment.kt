package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.adapters.FavoritesAdapter
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.models.ItemViewModel
import ru.teamdroid.recipecraft.room.Injection
import ru.teamdroid.recipecraft.room.ViewModelFactory
import ru.teamdroid.recipecraft.room.entity.Item

// TODO: TEMPORARY TESTING. IN NEXT VERSION HAVE TO REMOVE IT
class FavoritesFragment : BaseMoxyFragment() {

    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override val contentResId = R.layout.fragment_favorites

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ItemViewModel

    private val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = Injection.provideViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)

        fill()

        sendButton.setOnClickListener {
            if (!editTextView.text.isEmpty()) {
                insert(Item((Math.random() * 100).toInt(), editTextView.text.toString()))
                favoritesAdapter.notifyDataSetChanged()
            } else Toast.makeText(context, getString(R.string.warning_empty_field_text), Int.MAX_VALUE).show()
        }

        deleteAllButton.setOnClickListener {
            clearAll()
        }

        with(resultRecyclerView) {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun insert(item: Item) {
        disposable.add(viewModel.insert(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fill() },
                        { Toast.makeText(context, getString(R.string.room_error_text), Int.MAX_VALUE).show() }))
    }

    private fun fill() {
        disposable.add(viewModel.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favoritesAdapter.items = it },
                        { error -> Toast.makeText(context, error.message, Int.MAX_VALUE).show()}))
    }

    private fun clearAll() {
        disposable.add(viewModel.clearAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favoritesAdapter.items = emptyList() },
                        { error -> Toast.makeText(context, error.message, Int.MAX_VALUE).show()}))
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}