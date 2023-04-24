package sr.unasat.app;
import javax.swing.*;
import java.awt.*;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Applicatie extends JFrame implements ActionListener {
    JTextArea textArea = new JTextArea();
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu();
    JMenuItem open = new JMenuItem();

    Applicatie() throws FileNotFoundException {
        Map<String, String> variableMap = InVuller();
        Scanner inputPath = new Scanner(System.in);
        System.out.println("Paste/ Plak de path van uw text file nog eens");
        Path path = Paths.get(inputPath.next());
        Stream<String> lines;
        try {
            lines = Files.lines(path, Charset.forName("UTF-8"));
            List<String> replacedLines = lines.map(line -> Vervanger(line, variableMap))
                    .collect(Collectors.toList());
            Files.write(path, replacedLines, Charset.forName("UTF-8"));
            lines.close();
            Lezer();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        JFrame f = new JFrame();
//        JTextArea area = new JTextArea(Lezer() );
//        area.setBounds(10, 10, 600, 400);
//        area.setBackground(new Color(255, 240, 245));
//        f.add(area);
//        f.setSize(600, 400);
//        f.setLayout(null);
//        f.setVisible(true);
//        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.getContentPane().setBackground(new Color(255, 240, 245 ));


        setTitle("Open de .txt File");
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textArea = new JTextArea(40,40);
        textArea.setBackground(new Color(255, 240, 245));
        menuBar.add(file);
        file.setText("File");
        file.add(open);
        setJMenuBar(menuBar);
        open.setText("OPEN");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JScrollPane js = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(js);
        open.addActionListener(this);
    }


    public void actionPerformed( ActionEvent e) {
        if (e.getSource() == open)

            try
            {
                JFileChooser open = new JFileChooser();
                int option = open.showOpenDialog(this);
                File f1 = new File(open.getSelectedFile().getPath());
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);
                String s;
                while((s=br.readLine())!=null)
                {
                    textArea.append(s + "\n");
                }
                fr.close();
            }
            catch(Exception ae)
            {
                System.out.println(ae);
            }
    }

    public static void main( String args[] ) throws FileNotFoundException {
        new Applicatie();
    }


    public void Lezer() throws FileNotFoundException {

        String line = null;
        Scanner Path = new Scanner(System.in);
        System.out.println("Paste/ Plak de path van uw text file nog eens");
        File file = new File(Path.next());
        Scanner Scan = null;
        try {
            Scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (Scan.hasNextLine()) {
            line = Scan.nextLine();
            System.out.println(line);
        }    }



    public static Map<String, String> InVuller() throws FileNotFoundException {
        Map<String, String> map = new HashMap<String, String>();
        Scanner Path = new Scanner(System.in);
        System.out.println("Paste/ Plak de path van uw text file");
        File file = new File(Path.next());// copy path of Regels.tx
        Scanner scan = new Scanner(file);
        String text = scan.nextLine();
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) { // check als elk woord {{}} heeft
            Scanner inputUser = new Scanner(System.in);
            System.out.println(matcher.group(1) + "?");

            map.put(matcher.group(0), inputUser.next());

        }
        return map;
    }

    static String Vervanger( String str, Map<String, String> map ) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (str.contains(entry.getKey())) {
                str = str.replace(entry.getKey(), entry.getValue());
            }
        }
        return str;
    }

}
