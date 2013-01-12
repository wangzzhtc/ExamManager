package com.exam.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Label;

import com.exam.bean.Question;
import com.exam.util.ExamUtils;
import com.swtdesigner.SWTResourceManager;

public class reportComposite extends Composite {

	private Label candidateDisplay;
	private Label dateDisplay;
	private Label passScoreDisplay;
	private Label gradeDisplay;
	private Label elapsedTimeDisplay;
	private Label timeDisplay;
	private Label scoreDisplay;

	private int elapsedSeconds;
	private int score = 0;
	
	private ArrayList<Question> quesArray;

	public void setElapsedSeconds(int elapsedSeconds) {
		this.elapsedSeconds = elapsedSeconds;
	}
	
	public void setQuesResult(ArrayList<Question> quesArr){
		quesArray = quesArr;
	}

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public reportComposite(Composite parent, int style) {
		super(parent, style);
		
		
		// report composite
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.justify = true;
		rowLayout.marginTop = 5;
		setLayout(rowLayout);
		
		Composite composite = new Composite(this, SWT.NONE);
				
		Label lblTitle = new Label(composite, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblTitle.setBounds(220, 10, 72, 17);
		lblTitle.setText("\u8003\u8BD5\u5206\u6570\u62A5\u544A");
		
		Label lblCandidate = new Label(composite, SWT.NONE);
		lblCandidate.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblCandidate.setBounds(54, 45, 27, 17);
		lblCandidate.setText("\u8003\u751F:");
		
		Label lblDate = new Label(composite, SWT.NONE);
		lblDate.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblDate.setBounds(54, 85, 27, 17);
		lblDate.setText("\u65E5\u671F:");
		
		Label lblTime = new Label(composite, SWT.NONE);
		lblTime.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblTime.setBounds(302, 85, 51, 17);
		lblTime.setText("\u7ED3\u675F\u65F6\u95F4:");
		
		Label lblElapsedTime = new Label(composite, SWT.NONE);
		lblElapsedTime.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblElapsedTime.setBounds(302, 45, 51, 17);
		lblElapsedTime.setText("\u7B54\u9898\u65F6\u95F4:");
		
		Label lblPassScore = new Label(composite, SWT.NONE);
		lblPassScore.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblPassScore.setBounds(54, 125, 39, 17);
		lblPassScore.setText("\u5206\u6570\u7EBF:");
		
		Label lblScore = new Label(composite, SWT.NONE);
		lblScore.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblScore.setBounds(302, 125, 27, 17);
		lblScore.setText("\u6210\u7EE9:");
		
		Label lblGrade = new Label(composite, SWT.NONE);
		lblGrade.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblGrade.setBounds(54, 165, 51, 17);
		lblGrade.setText("\u8003\u8BD5\u7ED3\u679C:");

		
		composite.setLayoutData(new RowData(500, SWT.DEFAULT)); // adjust the height automatically.
		
		candidateDisplay = new Label(composite, SWT.NONE);
		candidateDisplay.setBounds(122, 45, 140, 17);
		candidateDisplay.setText("candidate");
		
		dateDisplay = new Label(composite, SWT.NONE);
		dateDisplay.setBounds(122, 85, 140, 17);
		dateDisplay.setText("date");
		
		passScoreDisplay = new Label(composite, SWT.NONE);
		passScoreDisplay.setBounds(122, 125, 140, 17);
		passScoreDisplay.setText("90 /  100");
		
		gradeDisplay = new Label(composite, SWT.NONE);
		gradeDisplay.setBounds(122, 165, 140, 17);
		gradeDisplay.setText("Pass");
		
		elapsedTimeDisplay = new Label(composite, SWT.NONE);
		elapsedTimeDisplay.setBounds(359, 45, 120, 17);
		elapsedTimeDisplay.setText("elasped time");
		
		timeDisplay = new Label(composite, SWT.NONE);
		timeDisplay.setBounds(359, 85, 120, 17);
		timeDisplay.setText("Time");
		
		scoreDisplay = new Label(composite, SWT.NONE);
		scoreDisplay.setBounds(359, 125, 120, 17);
		scoreDisplay.setText("95 / 100");
		
	}
	
	public void open(){
		createContents();
		initialization();
	}
	
	private void createContents(){
		

	}
	
	private void initialization(){
		Properties p = ExamUtils.getSystemProperties();
		candidateDisplay.setText(p.getProperty("candidate"));
		passScoreDisplay.setText(p.getProperty("PassScore") + " / " + p.getProperty("TotalScore"));
		
		score = calcFinalScore(Integer.valueOf(p.getProperty("TotalScore")));
		
		scoreDisplay.setText(score + " / " + p.getProperty("TotalScore"));
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		dateDisplay.setText(currentDate);
		String currentTime = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
		timeDisplay.setText(currentTime);
		
		String hh = String.format("%2d", elapsedSeconds / 3600);
		String mm = String.format("%2d", elapsedSeconds / 60 % 60);
		String ss = String.format("%2d", elapsedSeconds % 60);
		elapsedTimeDisplay.setText(hh + " 小时 " + mm + " 分钟 " + ss + " 秒");
		
		// process the grade display
		if(score >= Integer.valueOf(p.getProperty("PassScore"))){
			gradeDisplay.setText("Pass");
			gradeDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
			
		}
		else{
			gradeDisplay.setText("Failed");
			gradeDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		}
		
	
	}
	
	private int calcFinalScore(int totScore){
		int sc = 0;
		int numCorrectQues = 0;
		int totalNumQues = quesArray.size();

		Iterator<Question> itq = quesArray.iterator();
		while(itq.hasNext()){
			Question ques = (Question)itq.next();
//			System.out.println("Correct Ans:"+ques.getCorrectAnswer());
//			System.out.println("Given ans:"+ques.getGivenAnswer());
			if(ques.getCorrectAnswer().equals(ques.getGivenAnswer())){
				numCorrectQues++;
			}
		}
		
		sc = totScore * numCorrectQues / totalNumQues ;
//		System.out.println("Correct questions: " + numCorrectQues);

//		System.out.println((double)(totScore*numCorrectQues / totalNumQues));		
		return sc;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
