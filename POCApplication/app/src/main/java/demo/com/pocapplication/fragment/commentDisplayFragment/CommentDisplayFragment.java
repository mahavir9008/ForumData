package demo.com.pocapplication.fragment.commentDisplayFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.com.pocapplication.R;
import demo.com.pocapplication.Utility.Util;
import demo.com.pocapplication.adapter.RecycleAdapter;
import demo.com.pocapplication.di.baseModules.BaseFragment;
import demo.com.pocapplication.pojo.Comment;


public final class CommentDisplayFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView list;
    private RecycleAdapter recycleAdapter;
    @Inject
    CommentDisplayFragmentListener commentDisplayFragmentListener;
    private List<Comment> storeComment ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_display, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    /**
     * init the view
     */
    public void init() {
        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleAdapter = new RecycleAdapter(false, getActivity(), Item -> {

        });
        list.setAdapter(recycleAdapter);
    }

    @OnClick(R.id.postDialogButton)
    public void openMessageForm() {
        showCommentDialog();
    }


    /**
     *
     * @param commentData - add data to the view
     */
    public void addCommentData(List<Comment> commentData) {
        storeComment = new ArrayList<>();
        storeComment.addAll(commentData);
        recycleAdapter.addData(commentData);
    }

    /**
     * show dialog comment form to user
     */
    private void showCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.comment_form);
        View viewInflated = getActivity().getLayoutInflater().inflate(R.layout.form_comment, null);
        final EditText userName = viewInflated.findViewById(R.id.edt_name);
        final EditText userEmail = viewInflated.findViewById(R.id.edt_email);
        final EditText userComment = viewInflated.findViewById(R.id.edt_comment);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {

            if (!TextUtils.isEmpty(userName.getText()) && !TextUtils.isEmpty(userEmail.getText()) &&
                    !TextUtils.isEmpty(userComment.getText())) {
                dialog.dismiss();
                Comment comment = Util.createCommentObject(userName.getText().toString(), userEmail.getText().toString(), userComment.getText().toString()
                        , recycleAdapter.getUserId());
                storeComment.add(0,comment);
                recycleAdapter.addData(storeComment);
                commentDisplayFragmentListener.onCommentDisplayed(comment);
           }else{
                commentDisplayFragmentListener.onCommentFormEmpty();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
