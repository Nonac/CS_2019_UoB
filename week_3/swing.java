import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GridBagLayoutDemo
{
    //向JFrame中添加JButton按钮
    public static void makeButton(String title,JFrame frame,GridBagLayout gridBagLayout,GridBagConstraints constraints)
    {
        JButton button=new JButton(title);    //创建Button对象
        Font f=new Font("Times",Font.BOLD,15);
        button.setFont(f);
        gridBagLayout.setConstraints(button,constraints);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setText(title+1);
                frame.revalidate();
            }
        });
        frame.add(button);
    }
    public static void main(String[] agrs)
    {
        JFrame frame=new JFrame("OXO");
        GridBagLayout gbaglayout=new GridBagLayout();    //创建GridBagLayout布局管理器
        GridBagConstraints constraints=new GridBagConstraints();
        frame.setLayout(gbaglayout);    //使用GridBagLayout布局管理器
        constraints.fill=GridBagConstraints.BOTH;    //组件填充显示区域
        constraints.weightx=0.0;    //恢复默认值
        constraints.gridwidth = GridBagConstraints.REMAINDER;    //结束行
        begin(gbaglayout,constraints,frame);

        /*makeButton("7",frame,gbaglayout,constraints);    //调用方法，添加按钮组件
        makeButton("8",frame,gbaglayout,constraints);
        constraints.gridwidth=GridBagConstraints.REMAINDER;    //结束行
        makeButton("9",frame,gbaglayout,constraints);
        constraints.gridwidth=1;    //重新设置gridwidth的值

        makeButton("4",frame,gbaglayout,constraints);
        makeButton("5",frame,gbaglayout,constraints);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("6",frame,gbaglayout,constraints);
        constraints.gridwidth=1;

        makeButton("1",frame,gbaglayout,constraints);
        makeButton("2",frame,gbaglayout,constraints);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("3",frame,gbaglayout,constraints);
        constraints.gridwidth=1;

        makeButton("返回",frame,gbaglayout,constraints);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("拨号",frame,gbaglayout,constraints);
        constraints.gridwidth=1;*/
        frame.setBounds(400,400,400,400);    //设置容器大小
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static void begin(GridBagLayout gbaglayout,GridBagConstraints constraints,JFrame frame)
    {
        JTextField tf=new JTextField("You want a PVP or PVE? (y for PVP, n for PVE): ");
        final boolean[] pvp = {true};
        gbaglayout.setConstraints(tf, constraints);
        frame.add(tf);
        constraints.weightx=0.5;    // 指定组件的分配区域
        constraints.weighty=0.2;
        constraints.gridwidth=1;
        JButton buttony=new JButton("y");    //创建Button对象
        Font f=new Font("Times",Font.BOLD,15);
        buttony.setFont(f);
        gbaglayout.setConstraints(buttony,constraints);
        JButton buttonn=new JButton("n");    //创建Button对象
        buttonn.setFont(f);
        gbaglayout.setConstraints(buttonn,constraints);
        buttony.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp[0] =true;
                tf.setVisible(false);
                buttonn.setVisible(false);
                buttony.setVisible(false);
                begintwo(gbaglayout,constraints,frame);
                frame.setVisible(true);
            }
        });

        buttonn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp[0] =false;
                tf.setVisible(false);
                buttonn.setVisible(false);
                buttony.setVisible(false);

            }
        });
        frame.add(buttonn);
        frame.add(buttony);
        begintwo(gbaglayout,constraints,frame);
        frame.setVisible(true);
    }
    static void begintwo(GridBagLayout gbaglayout,GridBagConstraints constraints,JFrame frame)
    {
        JTextField tf=new JTextField("You want first? (y for first, n for second): ");
        final boolean[] pvp = {true};
        gbaglayout.setConstraints(tf, constraints);
        frame.add(tf);
        constraints.weightx=0.5;    // 指定组件的分配区域
        constraints.weighty=0.2;
        constraints.gridwidth=1;
        JButton buttony=new JButton("y");    //创建Button对象
        Font f=new Font("Times",Font.BOLD,15);
        buttony.setFont(f);
        gbaglayout.setConstraints(buttony,constraints);
        JButton buttonn=new JButton("n");    //创建Button对象
        buttonn.setFont(f);
        gbaglayout.setConstraints(buttonn,constraints);
        buttony.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp[0] =true;
            }
        });

        buttonn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pvp[0] =false;
            }
        });
        frame.add(buttonn);
        frame.add(buttony);
    }
}