package angry.dwarf.com.android101test.ui.base;

import android.view.View;

public interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    void OnItemClick(View view, int position);
  }

  @FunctionalInterface
  interface OnItemLongClickListener {
    void OnItemLongClick(View view, int position);
  }
}
