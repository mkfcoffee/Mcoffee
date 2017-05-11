package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.adapter.MeOptionsAdapter;
import personal.mcoffee.adapter.Mock.OptionMock;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.helper.DividerItemDecoration;
import personal.mcoffee.mvp.contract.MeContract;
import personal.mcoffee.mvp.model.Option;

/**
 * Created by Mcoffee.
 * 主界面_我的
 */

public class MyFragment extends BaseFragment implements MeContract.View{

    @BindView(R.id.me_toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    public static MyFragment getInstance() {
        return new MyFragment();
    }

    private  MeContract.Presenter presenter;

    @BindView(R.id.me_recyclerview)
    RecyclerView recyclerView;

    private MeOptionsAdapter meAdapter;

    private List<Option> datas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = OptionMock.provideMeOptions();
        if(meAdapter == null){
            meAdapter = new MeOptionsAdapter(datas,getContext());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        unbinder = ButterKnife.bind(this,view);
        toolbar.setTitle("个人中心");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fixToolbar(toolbar);
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(meAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void setPresenter(MeContract.Presenter presenter) {
       this.presenter = presenter;
    }
}
