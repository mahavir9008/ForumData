package demo.com.pocapplication.fragment.postDisplayFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import demo.com.pocapplication.R;
import demo.com.pocapplication.adapter.RecycleAdapter;
import demo.com.pocapplication.di.baseModules.BaseFragment;
import demo.com.pocapplication.model.ForumData;

public final class PostDisplayFragment extends BaseFragment {

    @Inject
    PostDisplayFragmentListener listener;
    @BindView(R.id.recycler_view)
    RecyclerView list;
    private RecycleAdapter recycleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_display, container, false);

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        init();
    }

    /**
     * init the view
     */
    public void init() {
        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleAdapter = new RecycleAdapter(true, getActivity(), Item -> listener.onItemClicked(((ForumData) Item).getUserId()));
        list.setAdapter(recycleAdapter);
    }

    /**
     *
     * @param forumData- form data show to the user
     */
    public void addData(List<ForumData> forumData) {
        recycleAdapter.addData(forumData);

    }

}