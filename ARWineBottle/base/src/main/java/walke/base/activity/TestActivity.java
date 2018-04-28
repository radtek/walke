package walke.base.activity;

import walke.base.R;

/**
 * Created by walke.Z on 2017/9/5.
 * //测试能否在主model(app)中直接用其他model依赖中的Activity
 * <p>
 *
 *  ButterKnife不能再在library module中使用,这是因为你的library中的
 *  R 字段的id值不是final类型的，但是你自己的应用module中却是final类型的。
 *  针对这个问题，有人在Jack的github上issue过这个问题，他本人也做了回答
 *
 */

public class TestActivity extends ButterKnifeActivity {
//    @BindView(R.id.test_TvButterKnife)
//    TextView mTestTvButterKnife;

    @Override
    public int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }



//    @OnClick(R.id.test_TvButterKnife)
//    public void onClick() {
//    }
}
