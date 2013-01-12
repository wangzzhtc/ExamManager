package com.exam.gui;

import java.util.ArrayList;
import java.util.Properties;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.exam.bean.ExamFile;
import com.exam.bean.Question;
import com.exam.util.ExamUtils;
import com.swtdesigner.SWTResourceManager;

public class ExamModeGUI {

	protected Shell shell;
	private Text txtName;
	private String path;
	private Spinner spinnerQues;
	private Button btnTime;
	private Button radio1;
	private Label lblExamTime;
	private Spinner spinnerTime;
	
	private ArrayList<Question> wholeQuesSet, selectedQuesSet;
	private Label lblPassingScore;
	private Label passScoreDisplay;
	private Properties examProp;

	
	public ExamModeGUI(String path){
		this.path = path;
		examProp = new Properties();
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		initialization();
		
		shell.open();
		shell.layout();
	
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE);
		shell.setSize(600, 518);
		shell.setText("\u8003\u9898\u8BBE\u7F6E");
		shell.setLocation(Display.getDefault().getPrimaryMonitor().getBounds().width / 2 - shell.getSize().x/2, Display.getDefault()
				.getPrimaryMonitor().getBounds().height / 2 - shell.getSize().y/2);
		
		txtName = new Text(shell, SWT.BORDER);
		txtName.setBounds(92, 22, 468, 23);
		
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setBounds(30, 25, 61, 17);
		lblname.setText("\u8003\u751F\u59D3\u540D");
		
		Group grpExamMode = new Group(shell, SWT.NONE);
		grpExamMode.setText("\u8BD5\u9898\u9009\u9879");
		grpExamMode.setBounds(30, 104, 530, 249);
		
		radio1 = new Button(grpExamMode, SWT.RADIO);
		radio1.setBounds(25, 32, 117, 17);
		radio1.setText("\u4ECE\u6240\u6709\u9898\u76EE\u4E2D\u9009\u505A");
		
		
		spinnerQues = new Spinner(grpExamMode, SWT.BORDER);
		spinnerQues.setBounds(148, 29, 47, 23);
		
		Group grpExamTime = new Group(shell, SWT.NONE);
		grpExamTime.setText("\u8003\u8BD5\u65F6\u95F4");
		grpExamTime.setBounds(30, 359, 530, 66);
		
		btnTime = new Button(grpExamTime, SWT.CHECK);
		btnTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeTimeStatus();
			}
		});
		btnTime.setBounds(22, 29, 98, 17);
		btnTime.setText("\u8BA1\u65F6\u5F00");
		
		lblExamTime = new Label(grpExamTime, SWT.NONE);
		lblExamTime.setBounds(171, 29, 96, 17);
		lblExamTime.setText("\u8003\u8BD5\u65F6\u95F4\uFF08\u5206\u949F\uFF09");
		
		spinnerTime = new Spinner(grpExamTime, SWT.BORDER);
		spinnerTime.setMaximum(720);
		spinnerTime.setMinimum(1);
		spinnerTime.setSelection(90);
		spinnerTime.setBounds(273, 26, 47, 23);
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setBounds(480, 458, 80, 27);
		btnCancel.setText("\u53D6\u6D88");
		
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startExam();
			}
		});
		btnOk.setBounds(394, 458, 80, 27);
		btnOk.setText("\u786E\u5B9A");
		
		lblPassingScore = new Label(shell, SWT.NONE);
		lblPassingScore.setBounds(30, 63, 61, 17);
		lblPassingScore.setText("\u901A\u8FC7\u5206\u6570");
		
		passScoreDisplay = new Label(shell, SWT.NONE);
		passScoreDisplay.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		passScoreDisplay.setBounds(92, 63, 468, 17);
		passScoreDisplay.setText("60 / 100");

		
	}
	
	private void startExam(){
		examProp.put("candidate",txtName.getText()); // save the candidate name

		ExamUtils.setSystemProperties(examProp);
		
		int examTime = -1;
		if(btnTime.getSelection()){
			examTime = spinnerTime.getSelection();
		}
		
		// retrieve the selected questions.
		ArrayList<String> ranSorted = ExamUtils.getRandomSortList(wholeQuesSet.size(), spinnerQues.getSelection());
		selectedQuesSet = new ArrayList<Question>();
		for (String s : ranSorted){
			selectedQuesSet.add(wholeQuesSet.get(Integer.valueOf(s)));
		}
		
		try {
			ExamGUI window = new ExamGUI(examTime, selectedQuesSet);
			shell.dispose();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialization(){
		ExamFile ef = (ExamFile) ExamUtils.decodeBinary(path);
		wholeQuesSet = ef.getQuesList();
		spinnerQues.setMinimum(1);
		spinnerQues.setSelection(ef.getQuesNum());
		spinnerQues.setMaximum(ef.getQuesNum());
		
		Properties p = ExamUtils.getSystemProperties();
		txtName.setText(p.getProperty("candidate"));
		radio1.setSelection(true);
		btnTime.setSelection(true);
		updateTimeSection(true);
		
		passScoreDisplay.setText(ef.getPassScore() + " / " + ef.getTotalScore());
		examProp.put("PassScore", "" + ef.getPassScore()); // save the passing score
		examProp.put("TotalScore", "" + ef.getTotalScore()); // save the total score
		
	}
	
	private void changeTimeStatus(){
		if(lblExamTime.getEnabled()){
			updateTimeSection(false);
		}
		else{
			updateTimeSection(true);
		}
	}
	
	private void updateTimeSection(boolean bo){
		lblExamTime.setEnabled(bo);
		spinnerTime.setEnabled(bo);
	}
}
