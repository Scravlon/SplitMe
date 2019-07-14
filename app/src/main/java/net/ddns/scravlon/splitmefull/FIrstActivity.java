package net.ddns.scravlon.splitmefull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class FIrstActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_first);
        /* Show/hide button */
/* Use skip button behavior */
/* Use back button behavior */
        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);

        /* Show/hide button */
        setButtonNextVisible(true);
        setButtonBackVisible(true);


/* Use next and finish button behavior */
/* Use next button behavior */

        addSlide(new SimpleSlide.Builder()
                .title("SPLITME")
                .description(R.string.splitdes)
                .image(R.drawable.splitmedeficon)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("SplitMe")
                .description(R.string.splitmedes)
                .image(R.drawable.splitme)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Split & Divide")
                .description(R.string.disdes)
                .image(R.drawable.sad)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("DivideMe")
                .description(R.string.dividedes)
                .image(R.drawable.divide)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Tax and discount")
                .description(R.string.saddes)
                .image(R.drawable.dicount)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Add Items")
                .description(R.string.slide2des)
                .image(R.drawable.plus)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_material_next)
                .scrollable(true)
                .build());


    }
}
