package activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import model.Beer;
import velimir.beersapp.R;
import static adapters.RecyclerListAdapter.BEER_OBJECT;


public class DetailActivity extends AppCompatActivity {

    public static final String SAVED_OBJECT = "SAVED_OBJECT";
    private ImageView imageView;
    private TextView name, description, tips;
    private Beer beer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        tips = findViewById(R.id.tips);

        if(savedInstanceState == null){
            getContent();
            setContent();
        } else {
            onRestoreInstanceState(savedInstanceState);
        }


    }

    private void getContent() {
        try {
            beer = getIntent().getExtras().getParcelable(BEER_OBJECT);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_OBJECT,beer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        beer = savedInstanceState.getParcelable(SAVED_OBJECT);
        setContent();
    }

    private void setContent(){
        Glide
                .with(this)
                .load(beer.getAvatar())
                .into(imageView);
        name.setText(beer.getName());
        description.setText("Description: " + beer.getDescription());
        tips.setText("Brewer tips: " + beer.getTips());
    }
}
