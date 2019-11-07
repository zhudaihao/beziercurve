package cn.zdh.beziercurve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DragBubbleView dragBubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragBubbleView=findViewById(R.id.drag_buddle_view);

        //path的API
//        setContentView(new PathView(this));
//        setContentView(new BezierViewMy(this));

        //实战
//        dragBubbleView = new DragBubbleView(this);
//        setContentView(dragBubbleView);
    }

    public void reset(View view) {
        dragBubbleView.reset();
    }
}
