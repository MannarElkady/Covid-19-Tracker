package com.example.covid_19tracker.UI.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.example.covid_19tracker.Database.CountyEntity

import com.example.covid_19tracker.R
import com.example.covid_19tracker.UI.Adapters.SubscripedRecycleViewAdapter
import com.example.covid_19tracker.ViewModels.SubscribedViewModel
import kotlinx.android.synthetic.main.subscribed_fragment.*

class SubscribedFragment : Fragment() {

    companion object {
        fun newInstance() =
            SubscribedFragment()
    }

    private lateinit var viewModel: SubscribedViewModel
    private lateinit var subscribeAdapter : SubscripedRecycleViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscribed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubscribedViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getFavouriteCountryList()?.observe(viewLifecycleOwner,Observer<List<CountyEntity>>{
            it?.let {
                setUpTableView(it)
            }
            if(it.size == 0){
                displayNoSubscription()
            }
        })
    }

    private fun displayNoSubscription() {
        no_subscription_layout.visibility = View.VISIBLE
    }

    private fun setUpTableView(countriesList : List<CountyEntity>) {
        subscribeAdapter = SubscripedRecycleViewAdapter(countriesList.toMutableList())
        recycle_view.layoutManager = LinearLayoutManager(context)
        recycle_view.adapter = subscribeAdapter
        recycle_view.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
       //recycle_view.numOfColumnsPerRowInGridList = 2
        recycle_view.swipeListener = onItemSwipeListener
        recycle_view.disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
        recycle_view.disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)
        //subscribeAdapter.notifyDataSetChanged();
    }
    private val onItemSwipeListener = object : OnItemSwipeListener<CountyEntity> {
        override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: CountyEntity): Boolean {
            // Handle action of item swiped
            // Return false to indicate that the swiped item should be removed from the adapter's data set (default behaviour)
            // Return true to stop the swiped item from being automatically removed from the adapter's data set (in this case, it will be your responsibility to manually update the data set as necessary)
            return false
        }
    }
}
