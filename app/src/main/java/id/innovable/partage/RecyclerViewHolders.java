package id.innovable.partage;

/**
 * Created by Qoharu on 2/24/17.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public ImageView markIcon;
    public TextView content;
    public TextView timestamp;
    public ImageView deleteIcon;
    private List<Content> contentObject;
    public RecyclerViewHolders(final View itemView, final List<Content> contentObject) {
        super(itemView);
        this.contentObject = contentObject;
        content = (TextView)itemView.findViewById(R.id.timeline_content);
        timestamp = (TextView)itemView.findViewById(R.id.timeline_timestamp);


    }
}