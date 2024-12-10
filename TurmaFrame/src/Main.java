import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Classe model.Aluno
class Aluno {
    private String nome;
    private double nota;

    public Aluno(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nome + " - Nota: " + nota;
    }
}

// Classe controller.Controlador
class Controlador {
    private List<Aluno> alunos = new ArrayList<>();

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void cadastrarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public boolean salvarAluno(Aluno aluno) {
        if (alunos.contains(aluno)) {
            return true;
        }
        return false;
    }

    public void listarAlunos() {
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }

    public void editarAluno(int index, Aluno aluno) {
        if (index >= 0 && index < alunos.size()) {
            alunos.set(index, aluno);
        }
    }

    public double calcularMedia() {
        return alunos.stream().mapToDouble(Aluno::getNota).average().orElse(0.0);
    }
}

// Classe view.AlunoFrame
class AlunoFrame extends JFrame {
    private JTextField nomeTextField;
    private JTextField notaTextField;
    private JButton okButton;
    private JButton cancelarButton;
    private Controlador controlador;

    public AlunoFrame(Controlador controlador) {
        this.controlador = controlador;

        setTitle("Cadastrar Aluno");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        nomeTextField = new JTextField();
        notaTextField = new JTextField();
        okButton = new JButton("OK");
        cancelarButton = new JButton("Cancelar");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                double nota = Double.parseDouble(notaTextField.getText());
                controlador.cadastrarAluno(new Aluno(nome, nota));
                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(new JLabel("Nome:"));
        add(nomeTextField);
        add(new JLabel("Nota:"));
        add(notaTextField);
        add(okButton);
        add(cancelarButton);
    }
}

// Classe view.TurmaFrame
class TurmaFrame extends JFrame {
    private JButton listarAlunosButton;
    private JButton novoAlunoButton;
    private JButton resultadosButton;
    private Controlador controlador;

    public TurmaFrame(Controlador controlador) {
        this.controlador = controlador;

        setTitle("Gerenciamento de Turmas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        listarAlunosButton = new JButton("Listar Alunos");
        novoAlunoButton = new JButton("Novo Aluno");
        resultadosButton = new JButton("Resultados");

        listarAlunosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.listarAlunos();
            }
        });

        novoAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AlunoFrame(controlador).setVisible(true);
            }
        });

        resultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double media = controlador.calcularMedia();
                JOptionPane.showMessageDialog(null, "Média da Turma: " + media);
            }
        });

        add(listarAlunosButton);
        add(novoAlunoButton);
        add(resultadosButton);
    }

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        new TurmaFrame(controlador).setVisible(true);
    }
}
