/**
 * Created by jonathanw on 7/3/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;


/** This class ties in all of the components from the GradeChart, DisplayData, and
 * DropDownLists class and puts them all onto one grid pane to be displayed in the scene
 */

//TODO: need to put an action event that extends to all of the other classes to refresh them
    // once a professor is chosen/ MAY HAVE TO MOVE THE ACTIONEVENT FROM THE DROPDOWN CLASS TO THIS ONE
public class DisplayAll extends Application
{
    DropDownList dropList;
    DisplayData displayData;
    GradeChart gradeChart;
    BackgroundImage backgroundImage;


    GridPane listPane;
    BorderPane pane;
    //TODO: use a layout pane on top of a grid pane to display everything somewhat easily
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();

            dropList = new DropDownList(db);

            displayData = new DisplayData(dropList.getReturndbAPI(), true);

            gradeChart = new GradeChart(dropList.getReturndbAPI(), true);

            setDropListPane();



            Image seal = new Image(new FileInputStream("resources/No BackGround TAMU Seal.png"));

            GridPane grid = new GridPane();
            grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(5,3,5,3));
            // grid.add(dropList.getChooseSubject(), 0,0);
            // grid.add(dropList.getChooseCourse(), 1, 0);
            // grid.add(dropList.getChooseProfessor(), 2, 0);
            // grid.add(displayData.getCourseInfo(), 3,1);
            grid.add(gradeChart.getBarChart(),0,3);
            grid.add(gradeChart.getLineChart(), 0, 6);
            grid.add(displayData.getPercentagesDisplay(),1, 3);
            grid.add(displayData.getTotalGrades(), 1, 6);

            pane.setCenter(grid);
            pane.setBackground(new Background(backgroundImage));

            Scene scene = new Scene(pane, 800, 800);

            // Supposedly changes the scene Icon
            primaryStage.getIcons().add(new Image(new FileInputStream("resources/Calligraphy J.png")));

            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showImageNotFound();
        }
    }

    private void setDropListPane()
    {
        listPane = new GridPane();

        listPane.setVgap(4);
        listPane.setHgap(10);
        listPane.setPadding(new Insets(5,3,5,5));
        listPane.add(dropList.getChooseSubject(), 0, 0);
        listPane.add(dropList.getChooseCourse(), 1, 0);
        listPane.add(dropList.getChooseProfessor(), 2,0);
        listPane.add(displayData.getCourseInfo(), 3, 0);


        pane = new BorderPane();
        pane.setTop(listPane);
        pane.setPadding(new Insets(5, 5, 5, 5));

    }

    //Hopefully sets the background image for the application but doesn't
    private void setBackground() throws FileNotFoundException
    {
        backgroundImage = new BackgroundImage(new Image("resources/No BackGround TAMU Seal.png",
                32,32,false, true)
                ,BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

//TODO: change the application ICON
    //TODO: put the TAMU seal somewhere
}
