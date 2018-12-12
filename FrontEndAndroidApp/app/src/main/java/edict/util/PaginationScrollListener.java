package edict.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Pagination
 * Created by  on 10/15/16.
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private int currentPage;
    private int totalPage;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        currentPage = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();

        int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();

        if (lastVisibleItemPosition == totalItemCount - 1) {

            if (morePages()) {

                loadPage(currentPage + 1);

            }
        }

    }

    public abstract void loadPage(int pageNumber);

    private boolean morePages() {
        return currentPage < totalPage;
    }

    public void onResponse(int totalPage) {
        this.totalPage = totalPage;
        currentPage++;
    }

}