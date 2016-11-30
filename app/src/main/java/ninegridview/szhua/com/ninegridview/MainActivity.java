package ninegridview.szhua.com.ninegridview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.szhua.ninegridview.NineGridView;
import com.szhua.ninegridview.NineGridViewImageControl;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NineAdapter nineAdapter =new NineAdapter(this) ;
        ArrayList<ArrayList<String>> datas =new ArrayList<>() ;
        for (int i= 0 ;i<9;i++){
            ArrayList<String> data =new ArrayList<>() ;
            if(i==3){   data.add("sssss");}else {
                for (int j = 0; j <= new Random().nextInt(9); j++) {
                    data.add("sssss");
                }
            }
            datas.add(data) ;
            Log.i("leilei","size:"+data.size());
        }
        nineAdapter.setLists(datas);
        recyclerView.setAdapter(nineAdapter);

    }
}
