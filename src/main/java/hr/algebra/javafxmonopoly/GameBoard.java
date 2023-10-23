package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.enums.CornerPaneType;
import hr.algebra.javafxmonopoly.enums.GamePane;
import hr.algebra.javafxmonopoly.enums.Group;
import hr.algebra.javafxmonopoly.enums.Type;
import hr.algebra.javafxmonopoly.models.CornerPane;
import hr.algebra.javafxmonopoly.models.MiscPane;
import hr.algebra.javafxmonopoly.models.PropertyPane;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends GridPane {

    private final String paneStyle = "-fx-background-color: #d2d2d2; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1;";

    private final List<GamePane> gamePanes = new ArrayList<>(40);

    public List<GamePane> getGamePanes() {
        return this.gamePanes;
    }

    public GameBoard() {
        createRowAndColumnConstraints();
        createImagePane();
        createEdgePanes();
    }

    private void createEdgePanes() {
        CornerPane p0 = new CornerPane(CornerPaneType.GO);
        this.add(p0, 10, 10);
        p0.setId(String.valueOf(0));
        gamePanes.add(p0);

        PropertyPane p1 = new PropertyPane("MEDITERRANEAN AVENUE", 60, Group.BROWN);
        this.add(p1, 9, 10);
        p1.setId(String.valueOf(1));
        gamePanes.add(p1);

        MiscPane p2 = new MiscPane("COMMUNITY CHEST", Type.COMMUNITY_CHEST, "");
        this.add(p2, 8, 10);
        p2.setId(String.valueOf(2));
        gamePanes.add(p2);

        PropertyPane p3 = new PropertyPane("BALTIC AVENUE", 60, Group.BROWN);
        this.add(p3, 7, 10);
        p3.setId(String.valueOf(3));
        gamePanes.add(p3);

        MiscPane p4 = new MiscPane("INCOME TAX", Type.INCOME_TAX, "Pay $200");
        this.add(p4, 6, 10);
        p4.setId(String.valueOf(4));
        gamePanes.add(p4);

        PropertyPane p5 = new PropertyPane("READING AIRPORT", 200, Group.AIRPORT);
        this.add(p5, 5, 10);
        p5.setId(String.valueOf(5));
        gamePanes.add(p5);

        PropertyPane p6 = new PropertyPane("ORIENTAL AVENUE", 100, Group.LIGHT_BLUE);
        this.add(p6, 4, 10);
        p6.setId(String.valueOf(6));
        gamePanes.add(p6);

        MiscPane p7 = new MiscPane("CHANCE", Type.CHANCE, "");
        this.add(p7, 3, 10);
        p7.setId(String.valueOf(7));
        gamePanes.add(p7);

        PropertyPane p8 = new PropertyPane("VERMONT AVENUE", 100, Group.LIGHT_BLUE);
        this.add(p8, 2, 10);
        p8.setId(String.valueOf(8));
        gamePanes.add(p8);

        PropertyPane p9 = new PropertyPane("CONNECTICUT AVENUE", 120, Group.LIGHT_BLUE);
        this.add(p9, 1, 10);
        p9.setId(String.valueOf(9));
        gamePanes.add(p9);

        CornerPane p10 = new CornerPane(CornerPaneType.JUST_VISITING);
        this.add(p10, 0, 10);
        p10.setId(String.valueOf(10));
        gamePanes.add(p10);

        PropertyPane p11 = new PropertyPane("SAINT CHARLES PLACE", 140, Group.PINK);
        this.add(p11, 0, 9);
        p11.setId(String.valueOf(11));
        gamePanes.add(p11);

        PropertyPane p12 = new PropertyPane("ELECTRIC COMPANY", 150, Group.COMPANY);
        this.add(p12, 0, 8);
        p12.setId(String.valueOf(12));
        gamePanes.add(p12);

        PropertyPane p13 = new PropertyPane("STATES AVENUE", 140, Group.PINK);
        this.add(p13, 0, 7);
        p13.setId(String.valueOf(13));
        gamePanes.add(p13);

        PropertyPane p14 = new PropertyPane("VIRGINIA AVENUE", 160, Group.PINK);
        this.add(p14, 0, 6);
        p14.setId(String.valueOf(14));
        gamePanes.add(p14);

        PropertyPane p15 = new PropertyPane("PENNSYLVANIA AIRPORT", 200, Group.AIRPORT);
        this.add(p15, 0, 5);
        p15.setId(String.valueOf(15));
        gamePanes.add(p15);

        PropertyPane p16 = new PropertyPane("SAINT JAMES PLACE", 180, Group.ORANGE);
        this.add(p16, 0, 4);
        p16.setId(String.valueOf(16));
        gamePanes.add(p16);

        MiscPane p17 = new MiscPane("COMMUNITY CHEST", Type.COMMUNITY_CHEST, "");
        this.add(p17, 0, 3);
        p17.setId(String.valueOf(17));
        gamePanes.add(p17);

        PropertyPane p18 = new PropertyPane("TENNESSEE AVENUE", 180, Group.ORANGE);
        this.add(p18, 0, 2);
        p18.setId(String.valueOf(18));
        gamePanes.add(p18);

        PropertyPane p19 = new PropertyPane("NEW YORK AVENUE", 200, Group.ORANGE);
        this.add(p19, 0, 1);
        p19.setId(String.valueOf(19));
        gamePanes.add(p19);

        CornerPane p20 = new CornerPane(CornerPaneType.FREE_PARKING);
        this.add(p20, 0, 0);
        p20.setId(String.valueOf(20));
        gamePanes.add(p20);

        PropertyPane p21 = new PropertyPane("KENTUCKY AVENUE", 220, Group.RED);
        this.add(p21, 1, 0);
        p21.setId(String.valueOf(21));
        gamePanes.add(p21);

        MiscPane p22 = new MiscPane("CHANCE", Type.CHANCE, "");
        this.add(p22, 2, 0);
        p22.setId(String.valueOf(22));
        gamePanes.add(p22);

        PropertyPane p23 = new PropertyPane("INDIAN AVENUE", 220, Group.RED);
        this.add(p23, 3, 0);
        p23.setId(String.valueOf(23));
        gamePanes.add(p23);

        PropertyPane p24 = new PropertyPane("ILLINOIS AVENUE", 240, Group.RED);
        this.add(p24, 4, 0);
        p24.setId(String.valueOf(24));
        gamePanes.add(p24);

        PropertyPane p25 = new PropertyPane("B & O AIRPORT", 200, Group.AIRPORT);
        this.add(p25, 5, 0);
        p25.setId(String.valueOf(25));
        gamePanes.add(p25);

        PropertyPane p26 = new PropertyPane("ATLANTIC AVENUE", 260, Group.YELLOW);
        this.add(p26, 6, 0);
        p26.setId(String.valueOf(26));
        gamePanes.add(p26);

        PropertyPane p27 = new PropertyPane("VENTNOR AVENUE", 260, Group.YELLOW);
        this.add(p27, 7, 0);
        p27.setId(String.valueOf(27));
        gamePanes.add(p27);

        PropertyPane p28 = new PropertyPane("WATERWORKS", 150, Group.COMPANY);
        this.add(p28, 8, 0);
        p28.setId(String.valueOf(28));
        gamePanes.add(p28);

        PropertyPane p29 = new PropertyPane("MARVIN GARDEN", 280, Group.YELLOW);
        this.add(p29, 9, 0);
        p29.setId(String.valueOf(29));
        gamePanes.add(p29);

        CornerPane p30 = new CornerPane(CornerPaneType.GO_TO_JAIL);
        this.add(p30, 10, 0);
        p30.setId(String.valueOf(30));
        gamePanes.add(p30);

        PropertyPane p31 = new PropertyPane("PACIFIC AVENUE", 300, Group.GREEN);
        this.add(p31, 10, 1);
        p31.setId(String.valueOf(31));
        gamePanes.add(p31);

        PropertyPane p32 = new PropertyPane("NORTH CAROLINA AVENUE", 300, Group.GREEN);
        this.add(p32, 10, 2);
        p32.setId(String.valueOf(32));
        gamePanes.add(p32);

        MiscPane p33 = new MiscPane("COMMUNITY CHEST", Type.COMMUNITY_CHEST, "");
        this.add(p33, 10, 3);
        p33.setId(String.valueOf(33));
        gamePanes.add(p33);

        PropertyPane p34 = new PropertyPane("PENNSYLVANIA AVENUE", 320, Group.GREEN);
        this.add(p34, 10, 4);
        p34.setId(String.valueOf(34));
        gamePanes.add(p34);

        PropertyPane p35 = new PropertyPane("SHORT AIRPORT", 200, Group.AIRPORT);
        this.add(p35, 10, 5);
        p35.setId(String.valueOf(35));
        gamePanes.add(p35);

        MiscPane p36 = new MiscPane("CHANCE", Type.CHANCE, "");
        this.add(p36, 10, 6);
        p36.setId(String.valueOf(36));
        gamePanes.add(p36);

        PropertyPane p37 = new PropertyPane("PARK PLACE", 350, Group.BLUE);
        this.add(p37, 10, 7);
        p37.setId(String.valueOf(37));
        gamePanes.add(p37);

        MiscPane p38 = new MiscPane("LUXURY TAX", Type.INCOME_TAX, "Pay $100");
        this.add(p38, 10, 8);
        p38.setId(String.valueOf(38));
        gamePanes.add(p38);

        PropertyPane p39 = new PropertyPane("PARK PLACE", 400, Group.BLUE);
        this.add(p39, 10, 9);
        p39.setId(String.valueOf(39));
        gamePanes.add(p39);
    }

    private void createRowAndColumnConstraints() {
        final RowConstraints rowsEdge = createRowConstraints(14);
        final RowConstraints rowsMid = createRowConstraints(8);

        final ColumnConstraints colEdge = createColumnConstraints(14);
        final ColumnConstraints colMid = createColumnConstraints(8);

        this.getColumnConstraints().addAll(colEdge, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colEdge);
        this.getRowConstraints().addAll(rowsEdge, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsEdge);
    }

    private RowConstraints createRowConstraints(int percentHeight) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(percentHeight);
        return rowConstraints;
    }

    private ColumnConstraints createColumnConstraints(int percentWidth) {
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(percentWidth);
        return colConstraints;
    }

    private void createImagePane() {
        StackPane imagePane = new StackPane();
        imagePane.setAlignment(Pos.CENTER);

        this.add(imagePane, 1, 1, 9, 9);

        bindPaneSizeProperties(imagePane);

        ImageView imageView = createImageView();

        imagePane.setStyle("-fx-background-color: #bffddb; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 2;");

        imagePane.getChildren().add(imageView);
    }

    private void bindPaneSizeProperties(Pane pane) {
        final DoubleBinding multipliedHeight = this.heightProperty().multiply(0.72);
        final DoubleBinding multipliedWidth = this.widthProperty().multiply(0.72);

        pane.maxHeightProperty().bind(multipliedHeight);
        pane.maxWidthProperty().bind(multipliedWidth);
        pane.minHeightProperty().bind(multipliedHeight);
        pane.minWidthProperty().bind(multipliedWidth);
        pane.prefHeightProperty().bind(multipliedHeight);
        pane.prefWidthProperty().bind(multipliedWidth);
    }

    private ImageView createImageView() {
        ImageView imageView = new ImageView(String.valueOf(JavaFXMonopolyApplication.class.getResource("img/monopoly-logo.png")));
        imageView.setPreserveRatio(true);

        imageView.setScaleX(0.2);
        imageView.setScaleY(0.2);
        imageView.setRotate(15);

        imageView.setSmooth(true);

        return imageView;
    }

}
