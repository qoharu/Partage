package id.innovable.partage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Content> contents;
    protected Context context;
    public RecyclerViewAdapter(Context context, List<Content> contents) {
        this.contents = contents;
        this.context = context;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_content, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, contents);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.content.setText(contents.get(position).getContent());
        holder.timestamp.setText(contents.get(position).getTimestamp());
    }
    @Override
    public int getItemCount() {
        return this.contents.size();
    }
}