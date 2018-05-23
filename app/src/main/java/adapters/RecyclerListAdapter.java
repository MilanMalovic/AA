package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import activities.DetailActivity;
import model.Beer;
import velimir.beersapp.R;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> {

    public static final String BEER_OBJECT = "BEER_OBJECT";
    private Context context;
    private LayoutInflater inflater;
    private List<Beer> lista;

    public RecyclerListAdapter(Context context, List<Beer> lista) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lista = lista;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Beer beer = lista.get(position);
        holder.itemPosition = position;
        holder.beerName.setText(beer.getName());
        Glide
                .with(context)
                .load(beer.getAvatar())
                .into(holder.avatar);



    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView beerName;
        public int itemPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.beerAvatar);
            beerName = itemView.findViewById(R.id.beerName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(BEER_OBJECT, lista.get(itemPosition));
                    context.startActivity(intent);
                }
            });




        }
    }
}
