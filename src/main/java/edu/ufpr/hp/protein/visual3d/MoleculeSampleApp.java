/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package edu.ufpr.hp.protein.visual3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.ufpr.hp.protein.domain.AminoAcid;
import edu.ufpr.hp.protein.domain.Point;
import edu.ufpr.hp.protein.domain.TopologyContact;
import edu.ufpr.hp.protein.energy.EnergyFunction;
import edu.ufpr.hp.protein.utils.AminoAcidSequenceUtils;
import edu.ufpr.hp.protein.utils.AminoAcidType;
import edu.ufpr.hp.protein.utils.MovementEnum;
import edu.ufpr.hp.protein.utils.MovementUtils;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * MoleculeSampleApp
 */
public class MoleculeSampleApp extends Application {

    final Group root = new Group();
    final Group axisGroup = new Group();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final double cameraDistance = 450;
    final Xform moleculeGroup = new Xform();
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    private static final int DISTANCE = 40;

    // private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] {
    // MovementEnum.U, MovementEnum.U,
    // MovementEnum.U, MovementEnum.L, MovementEnum.F, MovementEnum.R,
    // MovementEnum.R, MovementEnum.L, MovementEnum.F,
    // MovementEnum.U, MovementEnum.U, MovementEnum.D, MovementEnum.F,
    // MovementEnum.L, MovementEnum.F, MovementEnum.R,
    // MovementEnum.F, MovementEnum.D, MovementEnum.L, MovementEnum.U,
    // MovementEnum.U, MovementEnum.F, MovementEnum.U,
    // MovementEnum.F, MovementEnum.U, MovementEnum.F, MovementEnum.F,
    // MovementEnum.U, MovementEnum.L, MovementEnum.D,
    // MovementEnum.R, MovementEnum.F, MovementEnum.F, MovementEnum.U,
    // MovementEnum.D, MovementEnum.L, MovementEnum.U,
    // MovementEnum.F, MovementEnum.R, MovementEnum.L, MovementEnum.L,
    // MovementEnum.F, MovementEnum.L, MovementEnum.F,
    // MovementEnum.F, MovementEnum.R };
    //
    // private static final String PROTEIN_CHAIN =
    // "PHHHHHHHHHHPHHHHHHHPHHHHHHHHPHHHHHHHPPPHPHPHPPH";

    private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] { MovementEnum.F, MovementEnum.F,
        MovementEnum.L, MovementEnum.L, MovementEnum.U, MovementEnum.U, MovementEnum.D, MovementEnum.L, MovementEnum.L,
        MovementEnum.L, MovementEnum.D, MovementEnum.D, MovementEnum.U, MovementEnum.D, MovementEnum.D, MovementEnum.L,
        MovementEnum.F, MovementEnum.L, MovementEnum.L };

    private static final String PROTEIN_CHAIN = "HHHPPHHPHHHHHHHHHPPH";
    // private static final MovementEnum[] FIXED_SOLUTION =
    // new MovementEnum[] { MovementEnum.F, MovementEnum.F, MovementEnum.L,
    // MovementEnum.L, MovementEnum.F,
    // MovementEnum.R, MovementEnum.U, MovementEnum.L, MovementEnum.U,
    // MovementEnum.L, MovementEnum.U };
    // private static final String PROTEIN_CHAIN = "HHHPHHHPHHHH";

    private List<Point3D> aminoAcidsCordinates;
    private List<AminoAcid> aminoAcidsList;

    private static final String ENERGY_LABEL = "Energy: - %s";

    private static final String COLLISIONS_LABEL = "Collisions: %s";

    private Text energyText;

    private Text collisionsText;

    private int energy = 0;
    private int collisions = 0;

    private MeshView meshView = loadMeshView();

    private MeshView loadMeshView() {

        float[] points = { -5, 5, 0, -5, -5, 0, 5, 5, 0, 5, -5, 0 };
        float[] texCoords = { 1, 1, 1, 0, 0, 1, 0, 0 };
        int[] faces = { 2, 2, 1, 1, 0, 0, 2, 2, 3, 3, 1, 1 };

        TriangleMesh mesh = new TriangleMesh();

        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texCoords);
        mesh.getFaces().setAll(faces);

        return new MeshView(mesh);
    }

    public MoleculeSampleApp() {
        aminoAcidsList = new ArrayList<>();
        aminoAcidsCordinates = new ArrayList<Point3D>();
        energyText = new Text(String.format(ENERGY_LABEL, energy));
        collisionsText = new Text(String.format(COLLISIONS_LABEL, collisions));
    }

    private void buildScene() {

        root.getChildren().add(world);
    }

    private void buildCamera() {

        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }

    private void buildAxes() {

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
    }

    private void handleMouse(SubScene scene) {

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {

                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {

                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (me.isControlDown()) {
                    modifier = 0.1;
                }
                if (me.isShiftDown()) {
                    modifier = 10.0;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0); // +
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0); // -
                } else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    camera.setTranslateZ(newZ);
                } else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3); // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3); // -
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

        buildScene();
        buildCamera();
        // buildAxes();
        buildMolecule();

        RotateTransition rotate = rotate3dGroup(world);
        VBox layout = new VBox(createControls(rotate), createScene3D(root));

        Scene scene = new Scene(layout, 1024, 768, true);

        scene.setFill(Color.GREY);

        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private SubScene createScene3D(Group group) {

        SubScene scene3d = new SubScene(group, 1024, 768, true, SceneAntialiasing.BALANCED);
        scene3d.setFill(Color.rgb(10, 10, 40));
        scene3d.setCamera(camera);
        handleMouse(scene3d);
        return scene3d;
    }

    private HBox createControls(RotateTransition rotateTransition) {

        CheckBox cull = new CheckBox("Cull Back");
        meshView.cullFaceProperty()
            .bind(Bindings.when(cull.selectedProperty()).then(CullFace.BACK).otherwise(CullFace.NONE));
        CheckBox wireframe = new CheckBox("Wireframe");
        meshView.drawModeProperty()
            .bind(Bindings.when(wireframe.selectedProperty()).then(DrawMode.LINE).otherwise(DrawMode.FILL));

        CheckBox rotate = new CheckBox("Rotate");
        rotate.selectedProperty().addListener(observable -> {
            if (rotate.isSelected()) {
                rotateTransition.play();
            } else {
                rotateTransition.pause();
            }
        });

        energyText.setText(String.format(ENERGY_LABEL, energy));
        collisionsText.setText(String.format(COLLISIONS_LABEL, collisions));
        HBox controls = new HBox(10, rotate, energyText, collisionsText, cull, wireframe);
        controls.setPadding(new Insets(10));
        return controls;
    }

    private RotateTransition rotate3dGroup(Group group) {

        RotateTransition rotate = new RotateTransition(Duration.seconds(10), group);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }

    private void buildMolecule() {

        Xform moleculeXform = new Xform();

        AminoAcidType[] aminoAcidsType = AminoAcidSequenceUtils.fromStringSequence(PROTEIN_CHAIN);
        // adding first amino acid
        int x = 0;
        int y = 0;
        int z = 0;

        String lookingAxis = "Z+";
        String headAxis = "Y+";
        Point3D rotate = Rotate.Y_AXIS;

        addAminoAcidToView(x, y, z, aminoAcidsType[0], moleculeXform, false, null, null, false);
        aminoAcidsCordinates.add(new Point3D(x, y, z));
        aminoAcidsList.add(new AminoAcid(new Point(x, y, z), aminoAcidsType[0] == AminoAcidType.H
            ? edu.ufpr.hp.protein.domain.AminoAcidType.H : edu.ufpr.hp.protein.domain.AminoAcidType.P, 0));

        for (int i = 1; i < aminoAcidsType.length; i++) {
            MovementEnum move = FIXED_SOLUTION[i - 1];
            String newLookingToAxis = lookingAxis;
            String newHeadAxis = headAxis;
            switch (move) {
                case F: {
                    newLookingToAxis = lookingAxis;
                    newHeadAxis = headAxis;
                    switch (lookingAxis) {
                        case "Z+": {
                            z = z + DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                        }
                        case "Z-": {
                            z = z - DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                        }
                        case "Y+": {
                            y = y + DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        }
                        case "Y-": {
                            y = y - DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        }
                        case "X+": {
                            x = x + DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        }
                        case "X-": {
                            x = x - DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        }
                    }
                }
                    break;
                case U: {
                    newLookingToAxis = headAxis;
                    newHeadAxis = MovementUtils.inverterAxis(lookingAxis);
                    switch (headAxis) {
                        case "Y+":
                            y = y + DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "Y-":
                            y = y - DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "X-":
                            x = x - DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "X+":
                            x = x + DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "Z+":
                            z = z + DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                        case "Z-":
                            z = z - DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                    }
                    break;

                }
                case D: {
                    newLookingToAxis = MovementUtils.inverterAxis(headAxis);
                    newHeadAxis = lookingAxis;
                    switch (headAxis) {
                        case "Y+":
                            y = y - DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "Y-":
                            y = y + DISTANCE;
                            rotate = Rotate.Y_AXIS;
                            break;
                        case "X-":
                            x = x + DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "X+":
                            x = x - DISTANCE;
                            rotate = Rotate.Z_AXIS;
                            break;
                        case "Z+":
                            z = z - DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                        case "Z-":
                            z = z + DISTANCE;
                            rotate = Rotate.X_AXIS;
                            break;
                    }
                    break;
                }
                case L: {
                    newHeadAxis = headAxis;
                    switch (lookingAxis) {
                        case "Z+":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;

                                case "X+":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Z-":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;

                                case "X-":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;

                                case "X+":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;

                            }
                            break;
                        case "Y+":
                            switch (headAxis) {
                                case "X+":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Z+":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                            }
                            break;
                        case "Y-":
                            switch (headAxis) {
                                case "X+":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Z+":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                            }
                            break;
                        case "X+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                    }
                }
                    break;
                case R: {
                    newHeadAxis = headAxis;
                    switch (lookingAxis) {
                        case "Z+":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Z-":
                            switch (headAxis) {
                                case "Y+":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                            }
                            break;
                        case "Y+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "Y-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "X-";
                                    x = x - DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "X+";
                                    x = x + DISTANCE;
                                    rotate = Rotate.Z_AXIS;
                                    break;
                                case "X+":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "X-":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X+":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                            }
                            break;
                        case "X-":
                            switch (headAxis) {
                                case "Z+":
                                    newLookingToAxis = "Y+";
                                    y = y + DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Z-":
                                    newLookingToAxis = "Y-";
                                    y = y - DISTANCE;
                                    rotate = Rotate.Y_AXIS;
                                    break;
                                case "Y+":
                                    newLookingToAxis = "Z-";
                                    z = z - DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;
                                case "Y-":
                                    newLookingToAxis = "Z+";
                                    z = z + DISTANCE;
                                    rotate = Rotate.X_AXIS;
                                    break;

                            }
                            break;
                    }
                }

            }
            lookingAxis = newLookingToAxis;
            headAxis = newHeadAxis;
            Point3D point3d = new Point3D(x, y, z);
            boolean pointCollided = false;
            if (aminoAcidsCordinates.contains(point3d)) {
                pointCollided = true;
            }

            aminoAcidsCordinates.add(point3d);

            aminoAcidsList
                .add(
                    new AminoAcid(
                        new Point(x / 40, y / 40, z / 40), aminoAcidsType[i] == AminoAcidType.H
                            ? edu.ufpr.hp.protein.domain.AminoAcidType.H : edu.ufpr.hp.protein.domain.AminoAcidType.P,
                i));

            Point3D previousAminoAcid = aminoAcidsCordinates.get(aminoAcidsCordinates.size() - 2);
            addAminoAcidToView(x, y, z, aminoAcidsType[i], moleculeXform, true, rotate, previousAminoAcid,
                pointCollided);

        }

        Set<TopologyContact> contacts = EnergyFunction.getTopologyContacts(aminoAcidsList);

        energy = contacts.size();
        collisions = EnergyFunction.getCollisionsCount(aminoAcidsList);
        aminoAcidsList.stream().forEach(a -> {
            System.out.println(a.getId() + ":" + a.getPoint());
        });

        moleculeGroup.getChildren().add(moleculeXform);

        world.getChildren().addAll(moleculeGroup);
    }

    public void addAminoAcidToView(double x, double y, double z, AminoAcidType atomType, Xform moleculeXform,
                                   boolean putBond, Point3D rotate, Point3D lastAminoAcid, boolean collided) {

        final PhongMaterial aminoAcidColor = new PhongMaterial();
        if (!collided) {
            aminoAcidColor.setDiffuseColor(atomType.getDiffuseColor());
            aminoAcidColor.setSpecularColor(atomType.getSpecularColor());
        } else {
            aminoAcidColor.setDiffuseColor(Color.DARKBLUE);
            aminoAcidColor.setSpecularColor(Color.BLUE);
        }

        final PhongMaterial bondColor = new PhongMaterial();
        bondColor.setDiffuseColor(Color.DARKGREY);
        bondColor.setSpecularColor(Color.GREY);

        Xform newAminoAcidSideXform = new Xform();
        Xform newAminoAcidXform = new Xform();

        Sphere aminoAcidSphere = new Sphere(10.0);
        aminoAcidSphere.setMaterial(aminoAcidColor);
        aminoAcidSphere.setTranslateX(0.0);

        if (putBond) {
            double lastX = lastAminoAcid.getX();
            double lastY = lastAminoAcid.getY();
            double lastZ = lastAminoAcid.getZ();

            Cylinder bondCylinder = new Cylinder(5, 40);
            bondCylinder.setMaterial(bondColor);

            bondCylinder.setTranslateX((x + lastX) / 2);
            bondCylinder.setTranslateY((y + lastY) / 2);
            bondCylinder.setTranslateZ((z + lastZ) / 2);

            bondCylinder.setRotationAxis(rotate);
            bondCylinder.setRotate(90.0);

            newAminoAcidSideXform.getChildren().add(bondCylinder);
        }

        moleculeXform.getChildren().add(newAminoAcidSideXform);

        newAminoAcidSideXform.getChildren().add(newAminoAcidXform);

        newAminoAcidXform.getChildren().add(aminoAcidSphere);

        newAminoAcidXform.setTx(x);

        newAminoAcidXform.setTy(y);

        newAminoAcidXform.setTz(z);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}
