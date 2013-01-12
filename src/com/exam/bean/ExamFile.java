package com.exam.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title, descripiton;
	private ArrayList<Question> quesList;
	private int quesNum, totalScore, passScore;
	
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getPassScore() {
		return passScore;
	}
	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescripiton() {
		return descripiton;
	}
	public void setDescripiton(String descripiton) {
		this.descripiton = descripiton;
	}
	public ArrayList<Question> getQuesList() {
		return quesList;
	}
	public void setQuesList(ArrayList<Question> quesList) {
		this.quesList = quesList;
	}
	public int getQuesNum() {
		return quesNum;
	}
	public void setQuesNum(int quesNum) {
		this.quesNum = quesNum;
	}
	
	
	
	
}
