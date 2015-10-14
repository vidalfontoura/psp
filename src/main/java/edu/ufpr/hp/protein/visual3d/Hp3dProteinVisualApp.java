//
// package edu.ufpr.hp.protein.visual3d;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Set;
//
// import edu.ufpr.hp.protein.domain.AminoAcid;
// import edu.ufpr.hp.protein.domain.Point;
// import edu.ufpr.hp.protein.domain.TopologyContact;
// import edu.ufpr.hp.protein.energy.EnergyFunction;
// import edu.ufpr.hp.protein.utils.AminoAcidSequenceUtils;
// import edu.ufpr.hp.protein.utils.AminoAcidType;
// import edu.ufpr.hp.protein.utils.MovementEnum;
// import edu.ufpr.hp.protein.utils.MovementUtils;
// import javafx.application.Application;
// import javafx.event.EventHandler;
// import javafx.geometry.Insets;
// import javafx.geometry.Point3D;
// import javafx.geometry.Pos;
// import javafx.scene.DepthTest;
// import javafx.scene.Group;
// import javafx.scene.Node;
// import javafx.scene.PerspectiveCamera;
// import javafx.scene.Scene;
// import javafx.scene.input.KeyEvent;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.GridPane;
// import javafx.scene.paint.Color;
// import javafx.scene.paint.PhongMaterial;
// import javafx.scene.shape.Box;
// import javafx.scene.shape.Cylinder;
// import javafx.scene.shape.Sphere;
// import javafx.scene.transform.Rotate;
// import javafx.stage.Stage;
//
/// **
// *
// * @author vfontoura
// */
// public class Hp3dProteinVisualApp extends Application {
//
// final Group root = new Group();
// final Xform axisGroup = new Xform();
// final Xform moleculeGroup = new Xform();
// final Xform world = new Xform();
// final PerspectiveCamera camera = new PerspectiveCamera(true);
// final Xform cameraXform = new Xform();
// final Xform cameraXform2 = new Xform();
// final Xform cameraXform3 = new Xform();
// private static final double CAMERA_INITIAL_DISTANCE = -600;
// private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
// private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
// private static final double CAMERA_NEAR_CLIP = 0.1;
// private static final double CAMERA_FAR_CLIP = 10000.0;
// private static final double AXIS_LENGTH = 250.0;
// private static final double HYDROGEN_ANGLE = 104.5;
// private static final double CONTROL_MULTIPLIER = 0.1;
// private static final double SHIFT_MULTIPLIER = 10.0;
// private static final double MOUSE_SPEED = 0.1;
// private static final double ROTATION_SPEED = 2.0;
// private static final double TRACK_SPEED = 0.3;
//
// private static final int DISTANCE = 40;
//
// // private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] {
// // MovementEnum.U, MovementEnum.D,
// // MovementEnum.D, MovementEnum.F, MovementEnum.U, MovementEnum.U,
// // MovementEnum.R, MovementEnum.L,
// // MovementEnum.U };
// // private static final String PROTEIN_CHAIN = "PHHHHHHHPH";
// // Funcionando
//
// // private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] {
// // MovementEnum.U, MovementEnum.U,
// // MovementEnum.U, MovementEnum.L, MovementEnum.F, MovementEnum.R,
// // MovementEnum.R, MovementEnum.L, MovementEnum.F,
// // MovementEnum.U, MovementEnum.U, MovementEnum.D, MovementEnum.F,
// // MovementEnum.L, MovementEnum.F, MovementEnum.R,
// // MovementEnum.F, MovementEnum.D, MovementEnum.L, MovementEnum.U,
// // MovementEnum.U, MovementEnum.F, MovementEnum.U,
// // MovementEnum.F, MovementEnum.U, MovementEnum.F, MovementEnum.F,
// // MovementEnum.U, MovementEnum.L, MovementEnum.D,
// // MovementEnum.R, MovementEnum.F, MovementEnum.F, MovementEnum.U,
// // MovementEnum.D, MovementEnum.L, MovementEnum.U,
// // MovementEnum.F, MovementEnum.R, MovementEnum.L, MovementEnum.L,
// // MovementEnum.F, MovementEnum.L, MovementEnum.F,
// // MovementEnum.F, MovementEnum.R };
//
// private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] {
// MovementEnum.F, MovementEnum.F,
// MovementEnum.L, MovementEnum.L, MovementEnum.U, MovementEnum.U,
// MovementEnum.U, MovementEnum.L, MovementEnum.L,
// MovementEnum.L, MovementEnum.D, MovementEnum.D, MovementEnum.U,
// MovementEnum.D, MovementEnum.D, MovementEnum.L,
// MovementEnum.F, MovementEnum.L, MovementEnum.L };
// private static final String PROTEIN_CHAIN = "HHHPPHHPHHHHHHHHHPPH";
//
// // private static final MovementEnum[] FIXED_SOLUTION = new MovementEnum[] {
// // MovementEnum.D, MovementEnum.D,
// // MovementEnum.D, MovementEnum.R, MovementEnum.F, MovementEnum.R,
// // MovementEnum.R, MovementEnum.L,
// // MovementEnum.F, MovementEnum.U, MovementEnum.U, MovementEnum.D,
// // MovementEnum.F, MovementEnum.L,
// // MovementEnum.F, MovementEnum.R, MovementEnum.F, MovementEnum.D,
// // MovementEnum.L, MovementEnum.U,
// // MovementEnum.U, MovementEnum.F, MovementEnum.U, MovementEnum.F,
// // MovementEnum.U, MovementEnum.F,
// // MovementEnum.F, MovementEnum.U, MovementEnum.L, MovementEnum.U,
// // MovementEnum.L, MovementEnum.F,
// // MovementEnum.F, MovementEnum.D, MovementEnum.U, MovementEnum.R,
// // MovementEnum.U, MovementEnum.F,
// // MovementEnum.R, MovementEnum.L, MovementEnum.L, MovementEnum.F,
// // MovementEnum.L, MovementEnum.F,
// // MovementEnum.F, MovementEnum.R };
// // private static final String PROTEIN_CHAIN =
// // "PHHHHHHHHHHPHHHHHHHPHHHHHHHHPHHHHHHHPPPHPHPHPPH";
// private List<Point3D> aminoAcidsCordinates;
//
// private List<AminoAcid> aminoAcidsList;
//
// double mousePosX;
// double mousePosY;
// double mouseOldX;
// double mouseOldY;
// double mouseDeltaX;
// double mouseDeltaY;
//
// public Hp3dProteinVisualApp() {
// aminoAcidsList = new ArrayList<>();
// aminoAcidsCordinates = new ArrayList<Point3D>();
//
// }
//
// // private void buildScene() {
// // root.getChildren().add(world);
// // }
// private void buildCamera() {
//
// System.out.println("buildCamera()");
// root.getChildren().add(cameraXform);
// cameraXform.getChildren().add(cameraXform2);
// cameraXform2.getChildren().add(cameraXform3);
// cameraXform3.getChildren().add(camera);
// cameraXform3.setRotateZ(180.0);
//
// camera.setNearClip(CAMERA_NEAR_CLIP);
// camera.setFarClip(CAMERA_FAR_CLIP);
// camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
// cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
// cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
// }
//
// private void buildAxes() {
//
// System.out.println("buildAxes()");
// final PhongMaterial redMaterial = new PhongMaterial();
// redMaterial.setDiffuseColor(Color.DARKRED);
// redMaterial.setSpecularColor(Color.RED);
//
// final PhongMaterial greenMaterial = new PhongMaterial();
// greenMaterial.setDiffuseColor(Color.DARKGREEN);
// greenMaterial.setSpecularColor(Color.GREEN);
//
// final PhongMaterial blueMaterial = new PhongMaterial();
// blueMaterial.setDiffuseColor(Color.DARKBLUE);
// blueMaterial.setSpecularColor(Color.BLUE);
//
// final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
// final Box yAxis = new Box(1, AXIS_LENGTH, 1);
// final Box zAxis = new Box(1, 1, AXIS_LENGTH);
//
// xAxis.setMaterial(redMaterial);
// yAxis.setMaterial(greenMaterial);
// zAxis.setMaterial(blueMaterial);
//
// axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
// axisGroup.setVisible(false);
// world.getChildren().addAll(axisGroup);
// }
//
// private void handleMouse(Scene scene, final Node root) {
//
// scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//
// @Override
// public void handle(MouseEvent me) {
//
// System.out.println(me.getSceneX());
// System.out.println(me.getSceneY());
// mousePosX = me.getSceneX();
// mousePosY = me.getSceneY();
// mouseOldX = me.getSceneX();
// mouseOldY = me.getSceneY();
//
// }
// });
// scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
// @Override
// public void handle(MouseEvent me) {
//
// mouseOldX = mousePosX;
// mouseOldY = mousePosY;
// mousePosX = me.getSceneX();
// mousePosY = me.getSceneY();
// mouseDeltaX = (mousePosX - mouseOldX);
// mouseDeltaY = (mousePosY - mouseOldY);
//
// double modifier = 1.0;
//
// if (me.isControlDown()) {
// modifier = CONTROL_MULTIPLIER;
// }
// if (me.isShiftDown()) {
// modifier = SHIFT_MULTIPLIER;
// }
// if (me.isPrimaryButtonDown()) {
// cameraXform.ry
// .setAngle(cameraXform.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier *
// ROTATION_SPEED);
// cameraXform.rx
// .setAngle(cameraXform.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier *
// ROTATION_SPEED);
// } else if (me.isSecondaryButtonDown()) {
// double z = camera.getTranslateZ();
// double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
// camera.setTranslateZ(newZ);
// } else if (me.isMiddleButtonDown()) {
// cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * MOUSE_SPEED *
// modifier * TRACK_SPEED);
// cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * MOUSE_SPEED *
// modifier * TRACK_SPEED);
// }
// }
// });
// }
//
// private void handleKeyboard(Scene scene, final Node root) {
//
// scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
// @Override
// public void handle(KeyEvent event) {
//
// switch (event.getCode()) {
// case Z:
// cameraXform2.t.setX(0.0);
// cameraXform2.t.setY(0.0);
// camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
// cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
// cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
// break;
// case X:
// axisGroup.setVisible(!axisGroup.isVisible());
// break;
// case V:
// moleculeGroup.setVisible(!moleculeGroup.isVisible());
// break;
// }
// }
// });
// }
//
// private void buildMolecule() {
//
// Xform moleculeXform = new Xform();
//
// AminoAcidType[] aminoAcidsType =
// AminoAcidSequenceUtils.fromStringSequence(PROTEIN_CHAIN);
// // adding first amino acid
// int x = 0;
// int y = 0;
// int z = 0;
// boolean isStandUp = true;
//
// String lookingAxis = "Z+";
// String headAxis = "Y+";
// Point3D rotate = Rotate.Y_AXIS;
//
// addAminoAcidToView(x, y, z, aminoAcidsType[0], moleculeXform, false, null,
// null);
// aminoAcidsCordinates.add(new Point3D(x, y, z));
// aminoAcidsList.add(new AminoAcid(new Point(x, y, z), aminoAcidsType[0] ==
// AminoAcidType.H
// ? edu.ufpr.hp.protein.domain.AminoAcidType.H :
// edu.ufpr.hp.protein.domain.AminoAcidType.P));
//
// for (int i = 1; i < aminoAcidsType.length; i++) {
// MovementEnum move = FIXED_SOLUTION[i - 1];
// String newLookingToAxis = lookingAxis;
// String newHeadAxis = headAxis;
// switch (move) {
// case F: {
// newLookingToAxis = lookingAxis;
// newHeadAxis = headAxis;
// switch (lookingAxis) {
// case "Z+": {
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// case "Z-": {
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// case "Y+": {
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// }
// case "Y-": {
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// }
// case "X+": {
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// }
// case "X-": {
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// }
// }
// }
// break;
// case U: {
// newLookingToAxis = headAxis;
// newHeadAxis = MovementUtils.inverterAxis(lookingAxis);
// switch (headAxis) {
// case "Y+":
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y-":
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "X-":
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z+":
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Z-":
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
//
// }
// case D: {
// newLookingToAxis = MovementUtils.inverterAxis(headAxis);
// newHeadAxis = lookingAxis;
// switch (headAxis) {
// case "Y+":
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y-":
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "X-":
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z+":
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Z-":
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// }
// case L: {
// newHeadAxis = headAxis;
// switch (lookingAxis) {
// case "Z+":
// switch (headAxis) {
// case "Y+":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
//
// case "X+":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// }
// break;
// case "Z-":
// switch (headAxis) {
// case "Y+":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
//
// case "X-":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
//
// case "X+":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
//
// }
// break;
// case "Y+":
// switch (headAxis) {
// case "X+":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Z+":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// }
// break;
// case "Y-":
// switch (headAxis) {
// case "X+":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Z+":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// }
// break;
// case "X+":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y+":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// case "X-":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y+":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// }
// }
// break;
// case R: {
// newHeadAxis = headAxis;
// switch (lookingAxis) {
// case "Z+":
// switch (headAxis) {
// case "Y+":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// }
// break;
// case "Z-":
// switch (headAxis) {
// case "Y+":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// }
// break;
// case "Y+":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// case "Y-":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "X-";
// x = x - DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "X+";
// x = x + DISTANCE;
// rotate = Rotate.Z_AXIS;
// break;
// case "X+":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "X-":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// case "X+":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y+":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// }
// break;
// case "X-":
// switch (headAxis) {
// case "Z+":
// newLookingToAxis = "Y+";
// y = y + DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Z-":
// newLookingToAxis = "Y-";
// y = y - DISTANCE;
// rotate = Rotate.Y_AXIS;
// break;
// case "Y+":
// newLookingToAxis = "Z-";
// z = z - DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
// case "Y-":
// newLookingToAxis = "Z+";
// z = z + DISTANCE;
// rotate = Rotate.X_AXIS;
// break;
//
// }
// break;
// }
// }
//
// }
// lookingAxis = newLookingToAxis;
// headAxis = newHeadAxis;
// aminoAcidsCordinates.add(new Point3D(x, y, z));
// aminoAcidsList.add(new AminoAcid(new Point(x / 40, y / 40, z / 40),
// aminoAcidsType[i] == AminoAcidType.H
// ? edu.ufpr.hp.protein.domain.AminoAcidType.H :
// edu.ufpr.hp.protein.domain.AminoAcidType.P));
// Point3D lastAminoAcid = aminoAcidsCordinates.get(aminoAcidsCordinates.size()
// - 2);
// addAminoAcidToView(x, y, z, aminoAcidsType[i], moleculeXform, true, rotate,
// lastAminoAcid);
//
// }
//
// Set<TopologyContact> contacts =
// EnergyFunction.getTopologyContacts(aminoAcidsList);
// System.out.println(contacts.size());
//
// moleculeGroup.getChildren().add(moleculeXform);
//
// world.getChildren().addAll(moleculeGroup);
// }
//
// public void addAminoAcidToView(double x, double y, double z, AminoAcidType
// atomType, Xform moleculeXform,
// boolean putBond, Point3D rotate, Point3D lastAminoAcid) {
//
// final PhongMaterial aminoAcidColor = new PhongMaterial();
// aminoAcidColor.setDiffuseColor(atomType.getDiffuseColor());
// aminoAcidColor.setSpecularColor(atomType.getSpecularColor());
//
// final PhongMaterial bondColor = new PhongMaterial();
// bondColor.setDiffuseColor(Color.DARKGREY);
// bondColor.setSpecularColor(Color.GREY);
//
// Xform newAminoAcidSideXform = new Xform();
// Xform newAminoAcidXform = new Xform();
//
// Sphere aminoAcidSphere = new Sphere(10.0);
// aminoAcidSphere.setMaterial(aminoAcidColor);
// aminoAcidSphere.setTranslateX(0.0);
//
// if (putBond) {
// double lastX = lastAminoAcid.getX();
// double lastY = lastAminoAcid.getY();
// double lastZ = lastAminoAcid.getZ();
//
// Cylinder bondCylinder = new Cylinder(5, 40);
// bondCylinder.setMaterial(bondColor);
//
// bondCylinder.setTranslateX((x + lastX) / 2);
// bondCylinder.setTranslateY((y + lastY) / 2);
// bondCylinder.setTranslateZ((z + lastZ) / 2);
//
// bondCylinder.setRotationAxis(rotate);
// bondCylinder.setRotate(90.0);
//
// newAminoAcidSideXform.getChildren().add(bondCylinder);
// }
//
// moleculeXform.getChildren().add(newAminoAcidSideXform);
//
// newAminoAcidSideXform.getChildren().add(newAminoAcidXform);
//
// newAminoAcidXform.getChildren().add(aminoAcidSphere);
//
// newAminoAcidXform.setTx(x);
//
// newAminoAcidXform.setTy(y);
//
// newAminoAcidXform.setTz(z);
// }
//
// @Override
// public void start(Stage primaryStage) {
//
// // setUserAgentStylesheet(STYLESHEET_MODENA);
// System.out.println("start()");
//
// root.getChildren().add(world);
// root.setDepthTest(DepthTest.ENABLE);
//
// GridPane grid = new GridPane();
// grid.setAlignment(Pos.CENTER);
// grid.setHgap(10);
// grid.setVgap(10);
// grid.setPadding(new Insets(25, 25, 25, 25));
//
// // Text scenetitle = new Text("Welcome");
// // scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
// // grid.add(scenetitle, 0, 0, 2, 1);
//
// grid.setVisible(true);
// root.getChildren().add(grid);
//
// // buildScene();
// buildCamera();
// buildAxes();
// buildMolecule();
//
// Scene scene = new Scene(root, 1024, 768, true);
//
// scene.setFill(Color.GREY);
//
// handleKeyboard(scene, world);
// handleMouse(scene, world);
//
// primaryStage.setTitle("Molecule Sample Application");
// primaryStage.setScene(scene);
// primaryStage.show();
//
// scene.setCamera(camera);
// }
//
// /**
// * The main() method is ignored in correctly deployed JavaFX application.
// * main() serves only as fallback in case the application can not be
// * launched through deployment artifacts, e.g., in IDEs with limited FX
// * support. NetBeans ignores main().
// *
// * @param args the command line arguments
// */
// public static void main(String[] args) {
//
// launch(args);
// }
//
// }
