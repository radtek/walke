package walke.base.tool;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * 布局元素帮助类
 *
 * @author A-Rui(xiongrui0320@yahoo.com.cn)
 * @version 2013-5-13 下午02:43:40
 */
@SuppressWarnings("deprecation")
public class LayoutParamsUtil {
    private LayoutParamsUtil() {
    }

    public static ViewGroup.LayoutParams getVG_P_MM() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams getVG_P_MW() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams getVG_P_MV(int value) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value);
    }

    public static ViewGroup.LayoutParams getVG_P_WV(int value) {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, value);
    }

    public static ViewGroup.LayoutParams getVG_P_VM(int value) {
        return new ViewGroup.LayoutParams(value, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams getVG_P_VV(int wValue, int hValue) {
        return new ViewGroup.LayoutParams(wValue, hValue);
    }

    public static LinearLayout.LayoutParams getL_P_F0(float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
    }

    public static LinearLayout.LayoutParams getL_P_0F(float weight) {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
    }

    public static LinearLayout.LayoutParams getL_P_0M(float weight) {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
    }

    public static LinearLayout.LayoutParams getL_P_VM(int value) {
        return new LinearLayout.LayoutParams(value, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_0V(int value, float weight) {
        return new LinearLayout.LayoutParams(0, value, weight);
    }

    public static LinearLayout.LayoutParams getL_P_M0(float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
    }

    public static LinearLayout.LayoutParams getL_P_W0(float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, weight);
    }

    public static LinearLayout.LayoutParams getL_P_MV(int value, float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, value, weight);
    }

    public static LinearLayout.LayoutParams getL_P_MV(int value) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, value);
    }

    public static LinearLayout.LayoutParams getL_P_FF() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_MM() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_MM(float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, weight);
    }

    public static LinearLayout.LayoutParams getL_P_FW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getL_P_MW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getL_P_MW(float weight) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }

    public static LinearLayout.LayoutParams getL_P_0W(float weight) {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }

    public static LinearLayout.LayoutParams getL_P_WF() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_WM() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_WW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getL_P_FV(int value) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, value);
    }

    public static LinearLayout.LayoutParams getL_P_VF(int value) {
        return new LinearLayout.LayoutParams(value, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getL_P_WV(int value) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, value);
    }

    public static LinearLayout.LayoutParams getL_P_VW(int value) {
        return new LinearLayout.LayoutParams(value, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getL_P_VV(int wValue, int hValue) {
        return new LinearLayout.LayoutParams(wValue, hValue);
    }

    public static RelativeLayout.LayoutParams getR_P_FF() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_MM() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_FW() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getR_P_MW() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getR_P_WF() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_WM() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_WW() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getR_P_VF(int value) {
        return new RelativeLayout.LayoutParams(value, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_VM(int value) {
        return new RelativeLayout.LayoutParams(value, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getR_P_VW(int value) {
        return new RelativeLayout.LayoutParams(value, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getR_P_MV(int value) {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value);
    }

    public static RelativeLayout.LayoutParams getR_P_FV(int value) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, value);
    }

    public static RelativeLayout.LayoutParams getR_P_WV(int value) {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, value);
    }

    public static RelativeLayout.LayoutParams getR_P_VV(int wValue, int hValue) {
        return new RelativeLayout.LayoutParams(wValue, hValue);
    }

    public static TableLayout.LayoutParams getTB_P_FF() {
        return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
    }

    public static TableLayout.LayoutParams getTB_P_FW() {
        return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
    }

    public static TableLayout.LayoutParams getTB_P_VW(int value) {
        return new TableLayout.LayoutParams(value, TableLayout.LayoutParams.WRAP_CONTENT);
    }

    public static TableLayout.LayoutParams getTB_P_VV(int wValue, int hValue) {
        return new TableLayout.LayoutParams(wValue, hValue);
    }

    public static TableLayout.LayoutParams getTB_P_FV(int value) {
        return new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, value);
    }

    public static TableLayout.LayoutParams getTB_P_MM() {
        return new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static TableLayout.LayoutParams getTB_P_MW() {
        return new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static TableRow.LayoutParams getTR_P_FF() {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
    }

    public static TableRow.LayoutParams getTR_P_MM() {
        return new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static TableRow.LayoutParams getTR_P_FW() {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
    }

    public static TableRow.LayoutParams getTR_P_WF() {
        return new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
    }

    public static TableRow.LayoutParams getTR_P_WW() {
        return new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    }

    public static TableRow.LayoutParams getTR_P_VM(int value) {
        return new TableRow.LayoutParams(value, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static TableRow.LayoutParams getTR_P_VF(int value) {
        return new TableRow.LayoutParams(value, TableRow.LayoutParams.MATCH_PARENT);
    }

    public static TableRow.LayoutParams getTR_P_VW(int value) {
        return new TableRow.LayoutParams(value, TableRow.LayoutParams.WRAP_CONTENT);
    }

    public static TableRow.LayoutParams getTR_P_FV(int value) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, value);
    }

    public static TableRow.LayoutParams getTR_P_MV(int value) {
        return new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value);
    }

    public static TableRow.LayoutParams getTR_P_WV(int value) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, value);
    }

    public static TableRow.LayoutParams getTR_P_VV(int wValue, int hValue) {
        return new TableRow.LayoutParams(wValue, hValue);
    }

    public static TableRow.LayoutParams getTR_P_WW(float weight) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, weight);
    }

    public static TableRow.LayoutParams getTR_P_FW(float weight) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, weight);
    }

    public static TableRow.LayoutParams getTR_P_MW(float weight) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, weight);
    }

    public static TableRow.LayoutParams getTR_P_MV(int value, float weight) {
        return new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, value, weight);
    }

    public static TableRow.LayoutParams getTR_P_0V(int value, float weight) {
        return new TableRow.LayoutParams(0, value, weight);
    }

    public static TableRow.LayoutParams getTR_P_0W(float weight) {
        return new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight);
    }

    public static TableRow.LayoutParams getTR_P_0M(float weight) {
        return new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
    }

    public static TableRow.LayoutParams getTR_P_WM() {
        return new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams getFL_P_MW() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams getFL_P_MM() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams getFL_P_MV(int value) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, value);
    }

    public static AbsListView.LayoutParams getABSL_P_MV(int value) {
        return new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, value);
    }

    public static AbsListView.LayoutParams getABSL_P_WV(int value) {
        return new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, value);
    }

    public static AbsListView.LayoutParams getABSL_P_WW() {
        return new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static AbsListView.LayoutParams getABSL_P_VV(int width, int height) {
        return new AbsListView.LayoutParams(width, height);
    }

    public static AbsListView.LayoutParams getABSL_P_VW(int width) {
        return new AbsListView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static AbsoluteLayout.LayoutParams getABSOLUTE_P(int width, int height, int x, int y) {
        return new AbsoluteLayout.LayoutParams(width, height, x, y);
    }

}
