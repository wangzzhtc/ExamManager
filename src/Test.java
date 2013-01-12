import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.exam.bean.ExamFile;
import com.exam.bean.Question;
import com.exam.data.Data;
import com.exam.util.ExamUtils;



public class Test {
	
	ArrayList<Question> quesArray = new ArrayList<Question>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		Test test = new Test();
		test.addNewQuestions();

	}

	private void addNewQuestions(){
		
		ExamFile ef = new ExamFile();
		ef.setTitle("计算机应用基础知识");
		ef.setDescripiton("This is the description of the exam");
				
		
//		for(int i=1; i<=32; i++){
//			
//
//			Question sq = new Question();
//			sq.setQuestion("This is the "+i+" question, please provide your answers below.");
//			try {
//				BufferedImage bi = ImageIO.read(new File("img\\test.jpg"));
//				sq.setImage(ExamUtils.getCompressedImage(bi));
//			} catch (IOException e1) {
//
//				e1.printStackTrace();
//			}
//			//	sq.setImage(SWTResourceManager.getImage("img\\test.jpg"));
//			ArrayList<String> opt = new ArrayList<String>();
//			opt.add("A. This is the first option.");
//			opt.add("B. This is the second option");
//			opt.add("C. This is the third option");
//			opt.add("D. 这是最后一个答案。");
//			sq.setQuestionType(Data.EXAM_QUES_SINGLE);
//			sq.setOptions(opt);
//
//			sq.setCorrectAnswer("A");
//			quesArray.add(sq);
//			
//		}
		////////////////////////////////////
		
		Question sq = new Question();
		sq.setQuestion("一般认为，信息（information）是____");
		ArrayList<String> opt = new ArrayList<String>();
		opt.add("A. 数据");
		opt.add("B. 人们关心的事情的消息");
		opt.add("C. 反映物质及其运动属性及特征的原始事实");
		opt.add("D. 记录下来的可鉴别的符号");
		sq.setQuestionType(Data.EXAM_QUES_SINGLE);
		sq.setOptions(opt);
		sq.setCorrectAnswer("C");
		quesArray.add(sq);
		
		sq = new Question();
		sq.setQuestion("信息资源的开发和利用已经成为独立的产业，即 ____");
		opt = new ArrayList<String>();
		opt.add("A. 第二产业");
		opt.add("B. 第三产业");
		opt.add("C. 信息产业");
		opt.add("D. 房地产业");
		sq.setQuestionType(Data.EXAM_QUES_SINGLE);
		sq.setOptions(opt);
		sq.setCorrectAnswer("C");
		quesArray.add(sq);
		
		sq = new Question();
		sq.setQuestion("信息技术是在信息处理中所采取的技术和方法，也可看作是 ____的一种技术。");
		opt = new ArrayList<String>();
		opt.add("A. 信息存储功能");
		opt.add("B. 扩展人感觉和记忆功能");
		opt.add("C. 信息采集功能");
		opt.add("D. 信息传递功能");
		sq.setQuestionType(Data.EXAM_QUES_SINGLE);
		sq.setOptions(opt);
		sq.setCorrectAnswer("B");
		quesArray.add(sq);
		
		sq = new Question();
		sq.setQuestion("现代信息技术的主体技术是____等。");
		opt = new ArrayList<String>();
		opt.add("A. 新材料和新能量");
		opt.add("B. 电子技术、微电子技术、激光技术");
		opt.add("C. 计算机技术、通信技术、控制技术");
		opt.add("D. 信息技术在人类生产和生活中的各种具体应用");
		sq.setQuestionType(Data.EXAM_QUES_SINGLE);
		sq.setOptions(opt);
		sq.setCorrectAnswer("C");
		quesArray.add(sq);
		
		sq = new Question();
		sq.setQuestion("目前应用愈来愈广泛的优盘属于____技术。");
		opt = new ArrayList<String>();
		opt.add("A. 刻录");
		opt.add("B. 移动存储");
		opt.add("C. 网络存储");
		opt.add("D. 直接连接存储");
		sq.setQuestionType(Data.EXAM_QUES_SINGLE);
		sq.setOptions(opt);
		sq.setCorrectAnswer("B");
		quesArray.add(sq);
		
		
		/////////////////////////////////////
		
		
		
		
		ef.setQuesList(quesArray);
		ef.setQuesNum(quesArray.size());
		ef.setPassScore(60);
		ef.setTotalScore(100);
		
		ExamUtils.encodeBinary(ef, "理论题.sns");
		

	}
	

	


}
