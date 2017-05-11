package personal.mcoffee.mvp.contract;

import personal.mcoffee.mvp.presenter.BasePresenter;
import personal.mcoffee.mvp.view.BaseView;

/**
 * Created by Mcoffee.
 */

public interface MeContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
