package os;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;

 public  class main implements ActionListener {

	//front end
	private JFrame frame;
	private JPanel panel,panel1,panel2,panel3,panel4;
	private JButton result,graph,reset;
	private ButtonGroup bg;
	private JRadioButton fcfs;
	private JRadioButton scan;
	private JRadioButton c_scan;
	private JRadioButton c_look;
 	private JLabel l1,l2,l3,l4;
	private JTextField tf1,tf2,tf3,tf4;
	
	
	
	//backend
	private int reqs_count;
	private int cylinders_count;
	private Integer reqs[];
	private String algo_type;
	private int total_movments=0;
	private int initial_head_pos;
	private List<Integer> headSequence;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Disk Schedulling Algorithms");
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
 
		bg=new ButtonGroup();  
	    fcfs = new JRadioButton("FCFS");
	    scan = new JRadioButton("SCAN");
		c_scan = new JRadioButton("C-SCAN");
		c_look = new JRadioButton("C-LOOK");

		fcfs.setActionCommand("fcfs");
		scan.setActionCommand("scan");
		c_scan.setActionCommand("c_scan");
		c_look.setActionCommand("c_look");
		
		panel.add(fcfs);
		panel.add(scan);
		panel.add(c_scan);
		panel.add(c_look);
		
		bg.add(fcfs);
		bg.add(scan);
		bg.add(c_scan);
		bg.add(c_look);
		  
 		add_event_handler(fcfs,this);
 		add_event_handler(scan,this);
 		add_event_handler(c_scan,this);
 		add_event_handler(c_look,this);

 		
 		
		
		panel3=new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel3, BorderLayout.CENTER);


		panel1=new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel1.getLayout();
		panel1.setMaximumSize(new Dimension(32767, 7000));
		panel1.setVisible(false);
		
		l1=new JLabel("Number of disk requests");
		l2=new JLabel("Total number of cylinders");
		l4=new JLabel("Initial head position");

		tf1=new JTextField(4);
		tf1.setToolTipText("");
		tf2=new JTextField(4);
		tf2.setToolTipText("");
		tf4=new JTextField(4);
		tf4.setToolTipText("");
		
		panel1.add(l1);		
		panel1.add(tf1);
		panel1.add(l2);
		panel1.add(tf2);
		panel1.add(l4);
		panel1.add(tf4);
		
		add_event_handler(tf1,this);
 		add_event_handler(tf2,this);
 		add_event_handler(tf4,this);
 		
 		panel3.add(panel1);
 		
 		
 		
 		
 		
 		panel2=new JPanel();
 		panel2.setVisible(false);
 
 		l3=new JLabel("Requests");
 		l3.setToolTipText("");
 		//add commas after each request!!!
 		tf3=new JTextField(10);
 		tf3.setToolTipText("");
 		panel2.add(l3);
 		panel2.add(tf3);
 		
 		add_event_handler(tf3, this);
 		panel3.add(panel2);
 		
 		
 		
 		panel4=new JPanel();
		frame.getContentPane().add(panel4, BorderLayout.SOUTH);
 		result = new JButton("Result");
 		graph = new JButton("Graph");
 		reset = new JButton("Reset");
 		
 		add_event_handler(result,this);
 		add_event_handler(graph,this);
 		add_event_handler(reset,this);
 		
 		panel4.add(graph);
 		panel4.add(result);
 		panel4.add(reset);
 		result.setVisible(false);
 		graph.setVisible(false);
 		reset.setVisible(true);
 		  
		   
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 
		//get the source if the obj
		 if(e.getSource().equals(fcfs) ||e.getSource().equals(scan)||e.getSource().equals(c_scan)||e.getSource().equals(c_look)) {
			 panel1.setVisible(true);
			 disable(fcfs);
			 disable(scan);
			 disable(c_scan);
			 disable(c_look);
			 //get the selected btn
			 this.algo_type=bg.getSelection().getActionCommand();
			 
		 }
		 
		 if(e.getSource().equals(tf1)) {
			 if(!tf1.getText().matches("\\d+")) {
				 JOptionPane.showMessageDialog(null, "Only numerical values !", "Error", JOptionPane.ERROR_MESSAGE);
				 tf1.setText(null);
				 return;
			 }
			 disable(tf1);
		 }
		 
		 if(e.getSource().equals(tf2)) {
			 if(!tf2.getText().matches("\\d+")) {
				 JOptionPane.showMessageDialog(null, "Only numerical values !", "Error", JOptionPane.ERROR_MESSAGE);
				 tf2.setText(null);
				 return;
			 }
			 disable(tf2);
			
		 }
		 
		
		 
		 if(e.getSource().equals(tf4)) {
			 if(!tf4.getText().matches("\\d+")) {
				 JOptionPane.showMessageDialog(null, "Only numerical values !", "Error", JOptionPane.ERROR_MESSAGE);
				 tf4.setText(null);
				 return;
			 }
			 disable(tf4);
			 panel2.setVisible(true);
			  
		 }
		 
		 if(e.getSource().equals(tf3)) {
			 if(!tf3.getText().matches("^[0-9,]+")) {
				 JOptionPane.showMessageDialog(null, "Only numerical values !", "Error", JOptionPane.ERROR_MESSAGE);
				 tf3.setText(null);
				 return;
			 }
			 disable(tf3);
			 //here is the main logic !!!
			 this.initial_head_pos=Integer.parseInt(this.tf4.getText());
			 this.reqs_count=Integer.parseInt(this.tf1.getText());
			 this.reqs=new Integer[reqs_count];
			 
			 //cylinders : 0-tf2 ( inclusive from both sides )
			 this.cylinders_count=Integer.parseInt(this.tf2.getText());
			 extract_reqs();
			 
			 switch(this.algo_type) {
			 
			 case "fcfs":schedule_fcfs();
			             break;
			 
			 case "scan":schedule_scan();
			             break;
			 
			 case "c_scan":schedule_c_scan();
			               break;
			               
			               
			 case "c_look":schedule_c_look();
			               break;
			 }
			 
			 graph.setVisible(true);
			 result.setVisible(true);
			 disable(tf3);
		 }
		 
		 
		 if(e.getSource().equals(result)) {
				JOptionPane.showMessageDialog(null, "The total movments the disk has performed : "+total_movments,"Result",JOptionPane.PLAIN_MESSAGE);
		         
		 }
		 
		 
         if(e.getSource().equals(graph)) {
        	 showChart(headSequence, this.algo_type);
		 }
         
         if(e.getSource().equals(reset)) {
         	 //reset everything to default
        	 result.setVisible(false);
        	 graph.setVisible(false);
        	 panel1.setVisible(false);
        	 
        	 enable(graph);
        	 enable(result);
        	 
        	 enable(fcfs);
        	 enable(scan);
        	 enable(c_scan);
        	 enable(c_look);
        	 bg.clearSelection();
        	 
        	 enable(tf1);
        	 enable(tf2);
        	 enable(tf3);
        	 enable(tf4);
        	 tf1.setText(null);
        	 tf2.setText(null);
        	 tf3.setText(null);
        	 tf4.setText(null);
        	 
        	 panel2.setVisible(false);
        	 initial_head_pos=0;
        	 reqs_count=0;
        	 reqs=null;
        	 headSequence=null;
        	 cylinders_count=0;
        	 algo_type=null;
        	 total_movments=0;
        	 
		 }
		 
         
		  
	}
	
	
	
	
	private void schedule_fcfs() {
		
		headSequence = new ArrayList<>();
		headSequence.add(this.initial_head_pos);
		
		for(int c=0;c<this.reqs.length;c++) {
			this.total_movments+=Math.abs(initial_head_pos-reqs[c]);
			initial_head_pos=reqs[c];
			headSequence.add(reqs[c]);
		}
		
		
		return;
	}
	
	
	private void schedule_scan() {
		//i will depend on that the disk head will start to move to the right direction (higher reqs)
		//the disk will go to the end of the disk based to the head default initial direction , in our case it will 
		//only goes to the end of right direction
		 
		headSequence = new ArrayList<>();
		headSequence.add(this.initial_head_pos);
		
		List<Integer> greater_than_head=new ArrayList<>();
		List<Integer> smaller_than_head=new ArrayList<>();
		
		
		for(int c=0;c<this.reqs.length;c++) {
			if(reqs[c]>=initial_head_pos) greater_than_head.add(reqs[c]);
			else   smaller_than_head.add(reqs[c]);
		}
		
		//from smaller to larger : 2,55,564
		Collections.sort(greater_than_head);
		
		//from larger to smaller : 44,20,1 
		Collections.sort(smaller_than_head,Collections.reverseOrder());
		 
		
		for(int ele:greater_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
			 headSequence.add(ele);
		}
		
		//goes to the disk end (at right side)
		this.total_movments+=Math.abs(initial_head_pos-this.cylinders_count);
		initial_head_pos=this.cylinders_count;
		headSequence.add(this.cylinders_count);
		
		for(int ele:smaller_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
		     headSequence.add(ele);
		}
		
		
 		return;
	}
	
	
	
	private void schedule_c_scan() {
		//i will depend the head direction to the right also 

		headSequence = new ArrayList<>();
		headSequence.add(this.initial_head_pos);
		

		List<Integer> greater_than_head=new ArrayList<>();
		List<Integer> smaller_than_head=new ArrayList<>();
		
		
		for(int c=0;c<this.reqs.length;c++) {
			if(reqs[c]>=initial_head_pos) greater_than_head.add(reqs[c]);
			else   smaller_than_head.add(reqs[c]);
		}
		
		//from smaller to larger : 2,55,564
		Collections.sort(greater_than_head);
		Collections.sort(smaller_than_head);
		 
		
		for(int ele:greater_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
		     headSequence.add(ele);
		}
		
		//goes to the disk end (at right side)
		this.total_movments+=Math.abs(initial_head_pos-this.cylinders_count);
		initial_head_pos=this.cylinders_count;
		headSequence.add(this.cylinders_count);
		
		//goes to the disk end (at left side)
	    this.total_movments+=Math.abs(initial_head_pos-0);
		initial_head_pos=0;
		headSequence.add(0);
				
		for(int ele:smaller_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
		     headSequence.add(ele);
		}
				
 		return;
		 
	}
	
	
	
	
	private void schedule_c_look() {
		
		headSequence = new ArrayList<>();
		headSequence.add(this.initial_head_pos);
		
		//i will depend the head direction to the right also 

		List<Integer> greater_than_head=new ArrayList<>();
		List<Integer> smaller_than_head=new ArrayList<>();
		
		
		for(int c=0;c<this.reqs.length;c++) {
			if(reqs[c]>=initial_head_pos) greater_than_head.add(reqs[c]);
			else   smaller_than_head.add(reqs[c]);
		}
		
		//from smaller to larger : 2,55,564
		Collections.sort(greater_than_head);
		Collections.sort(smaller_than_head);
		 
		
		for(int ele:greater_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
		     headSequence.add(ele);
		}
		
 
		//goes to the disk end (at left side)
	    this.total_movments+=Math.abs(initial_head_pos-smaller_than_head.get(0));
		initial_head_pos=smaller_than_head.get(0);
		 headSequence.add(smaller_than_head.get(0));
				
		for(int ele:smaller_than_head) {
		     this.total_movments+=Math.abs(initial_head_pos-ele);
		     initial_head_pos=ele;
		     headSequence.add(ele);
		}
		
	
		return;
	}
	
	
	
	
	
	
	private void extract_reqs() {
		
		String x_with_commas=this.tf3.getText();
		String [] y=x_with_commas.split(",");
		
		for(int c=0;c<y.length;c++) {
			this.reqs[c]=Integer.parseInt(y[c].trim());
		}
		
		return;
	}
	
	
	private void disable(JComponent x) {
		x.setEnabled(false);
		return;
	}
	
	private void enable(JComponent x) {
		x.setEnabled(true);
		return;
	}
 	
	private void add_event_handler(AbstractButton x,ActionListener listener) {
		x.addActionListener(listener);
		return;
	}
	
	private void add_event_handler(JTextField x,ActionListener listener) {
		x.addActionListener(listener);
		return;
	}
	
	
	
	private XYSeriesCollection createDataset(List<Integer> headSequence) {

	    XYSeries series = new XYSeries("Disk Head Movement");

	    for (int i = 0; i < headSequence.size(); i++) {
	        series.add(i, headSequence.get(i));  
	    }

	    XYSeriesCollection dataset = new XYSeriesCollection();
	    dataset.addSeries(series);

	    return dataset;
	}

	
	

	private JFreeChart createChart(XYSeriesCollection dataset, String title) {

	    JFreeChart chart = ChartFactory.createXYLineChart(
	    		 title,
	    		 "",
		            "Requests (cylinders)",
		            dataset,
		            PlotOrientation.HORIZONTAL,
		            false,
		            true,
		            false
	    );

	    XYPlot plot = chart.getXYPlot();

	    // hide X axis
	    plot.getDomainAxis().setVisible(false);

	    // ⭐ ADD DOTS HERE ⭐
	    XYLineAndShapeRenderer renderer =
	            (XYLineAndShapeRenderer) plot.getRenderer();

	    renderer.setSeriesLinesVisible(0, true);
	    renderer.setSeriesShapesVisible(0, true);
	    renderer.setSeriesShapesFilled(0, true);

	     

	    return chart;
	}

	

	private void showChart(List<Integer> headSequence, String algoName) {

	    XYSeriesCollection dataset = createDataset(headSequence);
	    JFreeChart chart = createChart(dataset, algoName.toUpperCase() + " Disk Scheduling");

	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));

	    JFrame frame = new JFrame(algoName.toUpperCase() + " Graph");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setContentPane(chartPanel);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}


}
