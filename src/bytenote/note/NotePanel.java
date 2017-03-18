package bytenote.note;

import java.util.ArrayList;

import bytenote.InfoPanel;
import bytenote.JFXMain;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class NotePanel extends GridPane {
	
	/*
	 * 
	 * Note.cNotePrefHeight = 15;
	 * 
	 */
	
	public static double paneToWin = 1.0/3.0;
	
	public ArrayList<Note> notes = new ArrayList<Note>();
	public int minShownIndex = 0;
	
	public static int hvgap = 15;
	
	public Integer columns = 2;
	
	public Label placeHolder;
	
	public NotePanel() {
		super();
		
		ObservableList<ColumnConstraints> c = getColumnConstraints();
		c.clear();
		for (int i=0; i<columns; i++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHalignment(HPos.CENTER);
			cc.setHgrow(Priority.ALWAYS);
//			cc.setMinWidth(50*columns/*-(columns-1*15)*/);
			c.add(cc);
		}
		
		setPrefSize((JFXMain.mainStage.getWidth()*paneToWin)-JFXMain.mainStage.getWidth()/42, Math.max(JFXMain.mainStage.getHeight()-InfoPanel.defaultHeight*2, Math.ceil(getChildren().size()/2)*Note.cNotePrefHeight));
		setHgap(hvgap);
		setVgap(hvgap);
		setBorder( new Border( new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(8) ) ) );
		
		setVisible(true);
		
		/*NoteTypes.addToMap("type1", Color.BLUE);
		for (int i = 0; i < 16; i++) {
			addNote( new Note(i, "text"+i, "type1") );
		}*/
		
		placeHolder = new Label();
		placeHolder.setVisible(false);
		
		refreshLayout();
		
	}
	
	public void refreshLayout() {
		if(notes.size() == 1 && !getChildren().contains(placeHolder)) {
			getChildren().add(placeHolder);
		} else if(notes.size() == 2 && getChildren().contains(placeHolder)) {
			getChildren().remove(placeHolder);
		}
		ObservableList<Node> children = getChildren();
		for (int i = 0; i < children.size(); i++) {
			GridPane.setConstraints(children.get(i), i%columns, i/columns);
//			( (Note) children.get(i) ).setPrefSize(this.getWidth()/2-hvgap*3, this.getHeight()/Math.floor(children.size()/2)-hvgap*Math.floor(children.size()/2) );
			( (Label) children.get(i) ).setPrefSize(this.getWidth()/2-hvgap*3, this.getHeight()/Math.ceil(children.size()/2));
		}
	}
	
	public void c57run() {
		refreshLayout();
		for (int i = 0; i < getChildren().size(); i++) {
			if(getChildren().get(i) instanceof Note) {
				((Note) getChildren().get(i)).c57run();
			}
		}
		setPrefSize((JFXMain.root.getWidth()*paneToWin)-JFXMain.root.getWidth()/42, Math.max(JFXMain.root.getHeight()-InfoPanel.defaultHeight*2, Math.ceil(getChildren().size()/2)*Note.cNotePrefHeight));
			}
	
	public void addNote(Note note) {
		if(getChildren().size() >= note.priority && note.priority >= 0) {
			getChildren().add(note.priority, note);
			notes.add(note);
		} else if(note.priority < 0) {
//			System.err.println("Priority invalid. Setting priority to 0");
			note.priority = 0;
			addNote(note);
		} else {
//			System.err.println("Priority too low. Setting priority to "+getComponentCount());
			note.priority = getChildren().size();
			addNote(note);
		}
		
		
	}

	public void remove(Note note) {
		this.getChildren().remove(note);
	}
	
}
