package com.exam.gui;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;

import com.exam.bean.Question;
import com.exam.data.Data;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;



public class ExamGUI {

	protected Shell shell;
	private Label lblQuestion;
	private Label lblImage;
	private Button [] optionList;
	private Composite composite;
	private int curIndex = 1;
	private int totalQuestionNumber = 0;
	private final int offset = -1;
	private int remainSeconds, initialSeconds;

	private ArrayList<Question> quesArray;
	private ScrolledComposite scrolledComposite;
	private Label lblQuestionNumber;
	private Button btnPrevious;
	private Button btnNext;
	private Label lblTitle;
	private Button btnAnswer;
	private Button btnEnd;
	private Button btnPause;
	private Button btnInfo;
	private Button btnExit;
	private Label timeRemainDisplay;
	private Timer timer;
	private Label lblTime;


	/**
	 * Launch the application.
	 * @param args
	 * 
	 */
//	public static void main(String[] args) {
//		try {
//			ExamGUI window = new ExamGUI();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public ExamGUI(int min, ArrayList<Question> alsq){
		remainSeconds = min * 60;
		initialSeconds = remainSeconds;
		quesArray = alsq;
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
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
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				exitExam(e);
			}
		});
		shell.setSize(934, 575);
		shell.setText("\u8003\u8BD5\u7CFB\u7EDF");
		shell.setLayout(new FormLayout());
		shell.setLocation(Display.getDefault().getPrimaryMonitor().getBounds().width / 2 - shell.getSize().x/2, Display.getDefault()
				.getPrimaryMonitor().getBounds().height / 2 - shell.getSize().y/2);
		shell.setMaximized(true);

		btnPrevious = new Button(shell, SWT.NONE);
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				previousQuestion();
			}
		});
		FormData fd_btnPrevious = new FormData();
		fd_btnPrevious.left = new FormAttachment(0, 10);
		fd_btnPrevious.bottom = new FormAttachment(100, -10);
		btnPrevious.setLayoutData(fd_btnPrevious);
		btnPrevious.setText("\u4E0A\u4E00\u9053");

		btnNext = new Button(shell, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextQuestion();

			}
		});
		fd_btnPrevious.right = new FormAttachment(btnNext, -6);
		FormData fd_btnNext = new FormData();
		fd_btnNext.left = new FormAttachment(0, 96);
		fd_btnNext.bottom = new FormAttachment(100, -10);
		btnNext.setLayoutData(fd_btnNext);
		btnNext.setText("\u4E0B\u4E00\u9053");

		btnInfo = new Button(shell, SWT.NONE);
		fd_btnNext.right = new FormAttachment(btnInfo, -6);
		FormData fd_btnInfo = new FormData();
		fd_btnInfo.right = new FormAttachment(0, 262);
		fd_btnInfo.bottom = new FormAttachment(100, -10);
		fd_btnInfo.left = new FormAttachment(0, 182);
		btnInfo.setLayoutData(fd_btnInfo);
		btnInfo.setText("\u4FE1\u606F");

		btnEnd = new Button(shell, SWT.NONE);
		btnEnd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				endExam(e);
			}
		});
		FormData fd_btnEnd = new FormData();
		fd_btnEnd.right = new FormAttachment(100, -10);
		fd_btnEnd.bottom = new FormAttachment(100, -10);
		btnEnd.setLayoutData(fd_btnEnd);
		btnEnd.setText("\u7ED3\u675F\u8003\u8BD5");

		btnPause = new Button(shell, SWT.NONE);
		btnPause.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pausePressed();
			}
		});
		fd_btnEnd.left = new FormAttachment(btnPause, 6);
		FormData fd_btnPause = new FormData();
		fd_btnPause.left = new FormAttachment(100, -176);
		fd_btnPause.bottom = new FormAttachment(100, -10);
		fd_btnPause.right = new FormAttachment(100, -96);
		btnPause.setLayoutData(fd_btnPause);
		btnPause.setText("\u6682\u505C");

		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(100, -43);
		fd_label_1.right = new FormAttachment(100, -10);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);

		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(100, -10);
		fd_label.left = new FormAttachment(0, 10);
		label.setLayoutData(fd_label);

		scrolledComposite = new ScrolledComposite(shell, SWT.V_SCROLL);
		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.left = new FormAttachment(btnPrevious, 0, SWT.LEFT);
		fd_scrolledComposite.bottom = new FormAttachment(100, -77);
		fd_scrolledComposite.right = new FormAttachment(100);
		fd_scrolledComposite.top = new FormAttachment(0, 53);
		scrolledComposite.setLayoutData(fd_scrolledComposite);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite = new Composite(scrolledComposite, SWT.BORDER);
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.wrap = false;
		rl_composite.spacing = 10;
		rl_composite.marginHeight = 3;
		composite.setLayout(rl_composite);

		lblQuestion = new Label(composite, SWT.WRAP);

		lblQuestion.setLayoutData(new RowData(shell.getBounds().width-60, SWT.DEFAULT));

		lblQuestion.setText("What is the difference between a csd/dsu and a modem? this is something like you never knew.\r\n   hostmane gateway\r\n   intreface fast ethernet 0/0\r\nwhat's wrong with the command?\r\n");

		lblImage = new Label(composite, SWT.NONE);


		scrolledComposite.setContent(composite);
		//scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.setMinHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scrolledComposite.setMinWidth(100);


		btnAnswer = new Button(shell, SWT.NONE);
		fd_label.top = new FormAttachment(0, 43);
		FormData fd_btnAnswer = new FormData();
		fd_btnAnswer.left = new FormAttachment(btnEnd, 0, SWT.LEFT);
		fd_btnAnswer.top = new FormAttachment(0, 10);
		fd_btnAnswer.right = new FormAttachment(100, -10);
		btnAnswer.setLayoutData(fd_btnAnswer);
		btnAnswer.setText("\u663E\u793A\u7B54\u6848");

		lblTitle = new Label(shell, SWT.NONE);
		FormData fd_lblTitle = new FormData();
		fd_lblTitle.top = new FormAttachment(0, 15);
		fd_lblTitle.left = new FormAttachment(0, 10);
		lblTitle.setLayoutData(fd_lblTitle);
		lblTitle.setText("\u7B54\u9898");

		lblQuestionNumber = new Label(shell, SWT.NONE);
		FormData fd_lblQuestionNumber = new FormData();
		fd_lblQuestionNumber.right = new FormAttachment(lblTitle, 96, SWT.RIGHT);
		fd_lblQuestionNumber.top = new FormAttachment(btnAnswer, 5, SWT.TOP);
		fd_lblQuestionNumber.left = new FormAttachment(lblTitle, 16);
		lblQuestionNumber.setLayoutData(fd_lblQuestionNumber);
		lblQuestionNumber.setText("1 / 100");

		btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				shell.dispose();
			}
		});
		btnExit.setLayoutData(new FormData());
		btnExit.setText("\u9000\u51FA");
		FormData fd_btnExit = new FormData();
		fd_btnExit.left = new FormAttachment(btnPause, 6);
		fd_btnExit.right = new FormAttachment(100, -10);
		fd_btnExit.bottom = new FormAttachment(100, -10);
		btnExit.setLayoutData(fd_btnExit);

		lblTime = new Label(shell, SWT.NONE);
		FormData fd_lblTime = new FormData();
		fd_lblTime.bottom = new FormAttachment(100, -54);
		lblTime.setLayoutData(fd_lblTime);
		lblTime.setText("\u5269\u4F59\u65F6\u95F4 ");

		timeRemainDisplay = new Label(shell, SWT.NONE);
		fd_lblTime.right = new FormAttachment(100, -81);
		FormData fd_timeRemainDisplay = new FormData();
		fd_timeRemainDisplay.bottom = new FormAttachment(100, -54);
		fd_timeRemainDisplay.right = new FormAttachment(100, -27);
		timeRemainDisplay.setLayoutData(fd_timeRemainDisplay);
		timeRemainDisplay.setText("01:00:10");


		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				lblQuestion.setLayoutData(new RowData(shell.getBounds().width-60, SWT.DEFAULT)); //resize the question label.
			}
		});

		initialization();


		refreshComposite();
	}


	private void initialization(){

		curIndex = 0;
		totalQuestionNumber = quesArray.size();
		this.nextQuestion();
		startTimeDisplay();

	}

	private void previousQuestion(){
		processCurrentQuestion();
		Question tempsq = quesArray.get(curIndex-1+offset);
		updateQuestion(tempsq);
		curIndex--; //
		updatePage();
	}


	private void nextQuestion(){
		processCurrentQuestion();
		Question tempsq = quesArray.get(curIndex+1+offset);
		updateQuestion(tempsq);
		curIndex++; //
		updatePage();
	}

	// save the selected button
	private void processCurrentQuestion(){
		if(optionList!=null){
			int charIndex = (int)'A'; //Char 'A'
			String out = "";
			for(Button btn : optionList){
				if(btn.getSelection()){
					out += (char)charIndex;

				}
				charIndex++; //For 'ABCD'
			}
			//			System.out.println(out);
			quesArray.get(curIndex+offset).setGivenAnswer(out);
		}
	}

	private void updatePage(){
		lblQuestionNumber.setText(curIndex + " / " + totalQuestionNumber);
		if(curIndex == 1){
			if(totalQuestionNumber == 1){
				btnPrevious.setEnabled(false);
				btnNext.setEnabled(false);
			}
			else{
				btnPrevious.setEnabled(false);
				btnNext.setEnabled(true);
			}
			
		}
		else if(curIndex == totalQuestionNumber){
			btnPrevious.setEnabled(true);
			btnNext.setEnabled(false);
		}
		else{
			btnPrevious.setEnabled(true);
			btnNext.setEnabled(true);
		}
	}

	private void updateQuestion(Question tempsq){

		lblQuestion.setText(tempsq.getQuestion());

		if(tempsq.getImage()!=null){
			ImageData data = new ImageData(new ByteArrayInputStream(tempsq.getImage()));
			Image swtImage = new Image(Display.getCurrent(),data);

			int imageW = swtImage.getBounds().width;
			int imageH = swtImage.getBounds().height;
			lblImage.setLayoutData(new RowData(imageW, imageH));
			lblImage.setBackgroundImage(swtImage);
		}
		else{
			lblImage.setBackgroundImage(null);
			lblImage.setLayoutData(new RowData(0, 0));
		}


		// remove the previous buttons
		if(optionList!=null){
			for(Button btn : optionList){
				btn.dispose();
			}
		}

		optionList = new Button[tempsq.getOptions().size()];
		int quesType = 0;
		if(Data.EXAM_QUES_SINGLE == tempsq.getQuestionType()){
			quesType = SWT.RADIO;
		}
		else if (Data.EXAM_QUES_MULTI == tempsq.getQuestionType()){
			quesType = SWT.CHECK;
		}

		int index = 0;
		Iterator<String> ite = tempsq.getOptions().iterator();
		while(ite.hasNext()){
			optionList[index] = new Button(composite, quesType);
			optionList[index].setText(ite.next());
			index++;
		}

		setSelectedAnswers(tempsq.getGivenAnswer());
		refreshComposite();
	}


	// If a question is answered, set the button selected.
	private void setSelectedAnswers(String ans){
		int charInt = (int)'A';
		for(int i=0;i<ans.length();i++){
			int btnIndex = (int)ans.charAt(i) - charInt;
			optionList[btnIndex].setSelection(true);
		}
	}

	private void refreshComposite(){

		scrolledComposite.setMinHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y); //adjust the vertical scrolled bar.
		composite.layout(); // refresh the composite
	}


	private void endExam(SelectionEvent e){
		processCurrentQuestion();
		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING| SWT.OK | SWT.CANCEL);
		mb.setText("退出");
		mb.setMessage("确定要结束考试吗？");
		int rc = mb.open();
		if(e.doit == (rc == SWT.OK)){
			timer.cancel(); // terminate the timer thread.
			generateReport();
		}
	}
	
	private void exitExam(ShellEvent e){
		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING| SWT.OK | SWT.CANCEL);
		mb.setText("退出");
		mb.setMessage("确定要退出考试吗？");
		int rc = mb.open();
		if(rc == SWT.OK){
			timer.cancel(); // terminate the timer thread.
			e.doit = true; // Shell will be closed.
		}
		else if(rc == SWT.CANCEL){
			e.doit = false;
		}
	}

	private void generateReport() {
		setExamComponentsVisible(false);

		reportComposite rcomposite = new reportComposite(scrolledComposite, SWT.BORDER);
		rcomposite.setElapsedSeconds(initialSeconds-remainSeconds);
		rcomposite.setQuesResult(quesArray);
		
		rcomposite.open();
		scrolledComposite.setContent(rcomposite);

		scrolledComposite.setMinHeight(rcomposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scrolledComposite.setMinWidth(100);

	}

	private void setExamComponentsVisible(boolean bo){
		composite.setVisible(bo);
		lblTitle.setVisible(bo);
		lblQuestionNumber.setVisible(bo);
		btnAnswer.setVisible(bo);
		btnPrevious.setVisible(bo);
		btnNext.setVisible(bo);
		btnInfo.setVisible(bo);
		btnPause.setVisible(bo);
		btnEnd.setVisible(bo);
		lblTime.setVisible(bo);
		timeRemainDisplay.setVisible(bo);
	}
	
	private void pausePressed(){
		if(lblTime.getVisible()){
			timer.cancel();// pause the timer
			lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			timeRemainDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK );
			mb.setText("暂停");
			mb.setMessage("按确定返回考试。");
			int rc = mb.open();
			if(rc == SWT.OK){ // resume the timer thread. 
				lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				timeRemainDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				startTimeDisplay(); 
				
			}
		}
		
	}


	private void startTimeDisplay(){
		timer = new Timer();

		if(remainSeconds!=-60){ // Timer is enabled
			timer.scheduleAtFixedRate(new timeTask(), 0, 1000); // start immediately, interval 1 sec
		}
		else{	// Timer is disabled.
			lblTime.setVisible(false);
			timeRemainDisplay.setVisible(false);
			btnPause.setEnabled(false);
		}

	}


	class timeTask extends TimerTask {
		public void run() {

			String hh = String.format("%02d", remainSeconds / 3600);
			String mm = String.format("%02d", remainSeconds / 60 % 60);
			String ss = String.format("%02d", remainSeconds % 60);

			final String timeRemain = hh+":"+mm+":"+ss;

			Display.getDefault().syncExec(new Runnable() {
				public void run() {

					timeRemainDisplay.setText(timeRemain);
					
					// The initialized time is reached
					if(timeRemain.equals("00:00:00")){
						timer.cancel(); // Terminate timer thread
						examTimeUp();
					}

				}
			});
			remainSeconds--;
		}
	}

	private void examTimeUp(){
		lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		timeRemainDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		
		MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK );
		mb.setText("考试结束");
		mb.setMessage("考试时间已到，按确定退出。");
		int rc = mb.open();
		if(rc == SWT.OK){
			generateReport();
		}
	}
}





