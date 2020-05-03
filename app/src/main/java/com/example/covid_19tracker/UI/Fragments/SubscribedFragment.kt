package com.example.covid_19tracker.UI.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.NavigationGraphDirections

import com.example.covid_19tracker.R
import com.example.covid_19tracker.UI.Adapters.SubscripedRecycleViewAdapter
import com.example.covid_19tracker.ViewModels.SubscribedViewModel
import kotlinx.android.synthetic.main.subscribed_fragment.*
import me.ibrahimsn.lib.OnItemSelectedListener

class SubscribedFragment : Fragment() , SubscripedRecycleViewAdapter.OnItemSelected{

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
        recycle_view.visibility = View.INVISIBLE
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

    /*private val onItemSelectedListener= object : OnItemSelectedListener{
        override fun onItemSelect(pos: Int): Boolean {
            Log.i("yarb","item data ${subscribeAdapter.dataSet[pos].country}")
            return true
        }
    }*/


    private val onItemSwipeListener = object : OnItemSwipeListener<CountyEntity> {
        override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: CountyEntity): Boolean {
            if(direction == OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT){
                //delete
                // Handle action of item swiped
                // Return false to indicate that the swiped item should be removed from the adapter's data set (default behaviour)
                // Return true to stop the swiped item from being automatically removed from the adapter's data set (in this case, it will be your responsibility to manually update the data set as necessary)
                viewModel.deleteSubscribedCountry(item)
                return false
            }
            else if(direction == OnItemSwipeListener.SwipeDirection.LEFT_TO_RIGHT){
                //navigate to details
                val detailsAction = NavigationGraphDirections
                    .actionGlobalCountryDetails(item)
                view?.findNavController()?.navigate(R.id.action_global_countryDetails)
            }
            return true

        }
    }

    override fun onEntitySelected(position: Int) {
        Log.i("yarb","item data ${subscribeAdapter.dataSet[position].country}")
    }
}
