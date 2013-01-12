package com.exam.gui;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.exam.bean.ExamFile;
import com.exam.bean.ImportedFile;
import com.exam.util.ExamUtils;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class ManagerGUI {

	protected Shell shell;
	private Table table;
	private ArrayList<ImportedFile> filelist;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ManagerGUI window = new ManagerGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
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
		shell = new Shell();
		shell.setSize(670, 432);
		shell.setText("\u8003\u8BD5\u7BA1\u7406\u7CFB\u7EDF");
		shell.setLocation(Display.getDefault().getPrimaryMonitor().getBounds().width / 2 - shell.getSize().x/2, Display.getDefault()
				.getPrimaryMonitor().getBounds().height / 2 - shell.getSize().y/2);
		shell.setLayout(new FormLayout());


		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("\u6587\u4EF6");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmExam = new MenuItem(menu, SWT.CASCADE);
		mntmExam.setText("\u8003\u8BD5");

		Menu menu_2 = new Menu(mntmExam);
		mntmExam.setMenu(menu_2);

		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setText("\u5E2E\u52A9");

		Menu menu_3 = new Menu(menuItem);
		menuItem.setMenu(menu_3);

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.right = new FormAttachment(100, -6);
		fd_table.bottom = new FormAttachment(100, -6);
		fd_table.top = new FormAttachment(0, 43);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnTitle = new TableColumn(table, SWT.NONE);
		tblclmnTitle.setWidth(280);
		tblclmnTitle.setText("\u6807\u9898");

		TableColumn tblclmnFilepath = new TableColumn(table, SWT.NONE);
		tblclmnFilepath.setWidth(337);
		tblclmnFilepath.setText("\u8DEF\u5F84");

		Button btnStart = new Button(shell, SWT.NONE);
		FormData fd_btnStart = new FormData();
		fd_btnStart.left = new FormAttachment(0, 10);
		fd_btnStart.top = new FormAttachment(0, 10);
		btnStart.setLayoutData(fd_btnStart);
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openAnExam();
			}
		});
		btnStart.setText("\u5F00\u59CB...");

		Button btnAdd = new Button(shell, SWT.NONE);
		fd_btnStart.right = new FormAttachment(btnAdd, -6);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.left = new FormAttachment(0, 96);
		fd_btnAdd.top = new FormAttachment(0, 10);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openFileDiag();
			}
		});
		btnAdd.setText("\u589E\u52A0...");

		Button btnRemove = new Button(shell, SWT.NONE);
		fd_btnAdd.right = new FormAttachment(btnRemove, -6);
		FormData fd_btnRemove = new FormData();
		fd_btnRemove.right = new FormAttachment(0, 262);
		fd_btnRemove.top = new FormAttachment(0, 10);
		fd_btnRemove.left = new FormAttachment(0, 182);
		btnRemove.setLayoutData(fd_btnRemove);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeOneExam();
			}
		});
		btnRemove.setText("\u79FB\u9664");

		Button btnExit = new Button(shell, SWT.NONE);
		FormData fd_btnExit = new FormData();
		fd_btnExit.left = new FormAttachment(100, -86);
		fd_btnExit.right = new FormAttachment(100, -6);
		fd_btnExit.top = new FormAttachment(0, 10);
		btnExit.setLayoutData(fd_btnExit);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setText("\u9000\u51FA");



	}

	@SuppressWarnings("unchecked")
	private void initialization(){
		File file = new File("initial.lst");
		if(file.exists()){
			filelist = (ArrayList<ImportedFile>) ExamUtils.decodeBinary("initial.lst");
			refreshExamList();
		}
	}


	private void refreshExamList(){
		table.removeAll();
		for(ImportedFile imF : filelist){
			TableItem ti = new TableItem(table, SWT.DEFAULT);
			ti.setText(0, imF.getTitle());
			ti.setText(1, imF.getPath());
		}
	}

	private void openFileDiag() {
		FileDialog fileSelect=new FileDialog(shell,SWT.OPEN);   
		fileSelect.setFilterNames(new String[]{"*.sns", "All Files (*.*)"});   
		fileSelect.setFilterExtensions(new String[]{"*.sns", "*.*"});   
		String dir=fileSelect.open();
		if(dir!=null){
			boolean added = false;
			for(ImportedFile f : filelist){ // check if the file is already added
				if(dir.equals(f.getPath())){
					added = true;
					break;
				}
			}
			if(!added){
				if(ExamUtils.decodeBinary(dir) instanceof ExamFile){

					ExamFile ef = (ExamFile) ExamUtils.decodeBinary(dir);
					ImportedFile imp = new ImportedFile();
					imp.setTitle(ef.getTitle());
					imp.setPath(dir);
					filelist.add(imp);
					ExamUtils.encodeBinary(filelist, "initial.lst");

					refreshExamList();
				}
				else{
					MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR| SWT.OK );
					mb.setText("错误");
					mb.setMessage("无法打开选定的文件，请重新选择！\r\n按确定继续。");
					mb.open();
				}
			}
			else{
				MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK );
				mb.setText("注意");
				mb.setMessage("已添加过此文件，请重新选择！\r\n按确定继续。");
				mb.open();
			}
		}
	}

	private void openAnExam(){
		if(table.getSelectionIndex()!=-1){
			String directory = table.getItem(table.getSelectionIndex()).getText(1);
			File file = new File(directory);
			if(file.exists()){
				try {
					ExamModeGUI window = new ExamModeGUI(directory);
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR| SWT.OK );
				mb.setText("错误");
				mb.setMessage("无法加载选择的试题，文件已损坏或被移除，请重新选择！\r\n按确定继续。");
				mb.open();
			}

		}
		else{
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK );
			mb.setText("注意");
			mb.setMessage("请先选择一门考题！\r\n按确定继续。");
			mb.open();
		}



	}

	private void removeOneExam(){

		int selectedInd = table.getSelectionIndex();
		if (selectedInd!=-1) {

			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK | SWT.CANCEL );
			mb.setText("移除");
			mb.setMessage("确定要移除选定的试题吗？");
			int rc = mb.open();
			if(rc==SWT.OK){
				filelist.remove(selectedInd);
				ExamUtils.encodeBinary(filelist, "initial.lst");
				refreshExamList();

				if (selectedInd < table.getItemCount()) {
					table.setSelection(selectedInd);
					table.setFocus();
				} else {
					table.setSelection(selectedInd - 1);
					table.setFocus();
				}
			}

		} else {
			MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK );
			mb.setText("注意");
			mb.setMessage("没有选中的考题！");
			mb.open();

		}
	}
}
