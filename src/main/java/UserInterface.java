import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.model.Aluno;
import org.example.model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserInterface {
    JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTextField nomeAlunoField;
    private JTextField telefoneAlunoField;
    private JComboBox<String> cursoComboBox;
    private JTextArea displayAlunosArea;
    private JButton addAlunoButton;
    private JButton listAlunoButton;
    private JButton deleteAlunoButton;
    private JButton updateAlunoButton;
    private JTextField nomeCursoField;
    private JTextField siglaCursoField;
    private JComboBox<Curso.Area> areaCursoComboBox;
    private JButton addCursoButton;
    private JTextArea displayCursosArea;
    private JButton listCursoButton;
    private JButton updateCursoButton;
    private JButton deleteCursoButton;
    private JComboBox<String> maioridadeComboBox;
    private JComboBox<String> sexoComboBox;
    private AlunoDAO alunoDAO;
    private CursoDAO cursoDAO;

    public UserInterface() {
        alunoDAO = new AlunoDAO();
        cursoDAO = new CursoDAO();


        populateCursoComboBox();
        populateAreaComboBox();

        maioridadeComboBox.addItem("Sim");
        maioridadeComboBox.addItem("Não");

        sexoComboBox.addItem("M");
        sexoComboBox.addItem("F");
        sexoComboBox.addItem("Outro");


        // Ação ao adicionar Aluno
        addAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAluno();
            }
        });

        // Listar alunos
        listAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAlunos();
            }
        });

        // Ação ao deletar Aluno
        deleteAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAluno();
            }
        });

        // Ação ao atualizar Aluno
        updateAlunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAluno();
            }
        });

        // Ação ao adicionar Curso
        addCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCurso();
            }
        });

        // Listar cursos
        listCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCursos();
            }
        });

        // Ação ao deletar Curso
        deleteCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCurso();
            }
        });

        // Ação ao atualizar Curso
        updateCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurso();
            }
        });
    }


    private void populateCursoComboBox() {
        cursoComboBox.removeAllItems();
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            cursoComboBox.addItem(curso.getSigla());
        }
    }


    private void populateAreaComboBox() {
        areaCursoComboBox.removeAllItems();
        for (Curso.Area area : Curso.Area.values()) {
            areaCursoComboBox.addItem(area);
        }
    }

    private void addAluno() {
        String nome = nomeAlunoField.getText();
        String telefone = telefoneAlunoField.getText();

        // Captura a maioridade como boolean
        boolean maioridade = maioridadeComboBox.getSelectedItem().equals("Sim");

        // Captura o valor de sexo
        String sexo = (String) sexoComboBox.getSelectedItem();

        String cursoSigla = (String) cursoComboBox.getSelectedItem();
        Curso curso = cursoDAO.findBySigla(cursoSigla).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Cria um novo aluno com os valores capturados
        Aluno aluno = new Aluno(null, nome, telefone, maioridade, curso, sexo);

        // Chama o DAO para adicionar o aluno no banco de dados
        alunoDAO.create(aluno);
        JOptionPane.showMessageDialog(null, "Aluno adicionado com sucesso!");
    }


    private void listAlunos() {
        List<Aluno> alunos = alunoDAO.findAll();
        StringBuilder builder = new StringBuilder();
        for (Aluno aluno : alunos) {
            builder.append(aluno.toString()).append("\n");
        }
        displayAlunosArea.setText(builder.toString());
    }

    private void deleteAluno() {

        Long matricula = Long.parseLong(JOptionPane.showInputDialog("Digite a matrícula do aluno a ser deletado:"));
        alunoDAO.findById(matricula).ifPresent(alunoDAO::delete);
        JOptionPane.showMessageDialog(null, "Aluno deletado com sucesso!");
    }

    private void updateAluno() {
        Long matricula = Long.parseLong(JOptionPane.showInputDialog("Digite a matrícula do aluno:"));
        Aluno aluno = alunoDAO.findById(matricula).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));


        String novoNome = JOptionPane.showInputDialog("Novo nome:", aluno.getNome());
        aluno.setNome(novoNome);

        String novoTelefone = JOptionPane.showInputDialog("Novo telefone:", aluno.getTelefone());
        aluno.setTelefone(novoTelefone);

        boolean novaMaioridade = JOptionPane.showInputDialog("Maioridade (Sim/Não):", aluno.isMaioridade() ? "Sim" : "Não").equalsIgnoreCase("Sim");
        aluno.setMaioridade(novaMaioridade);

        String novoSexo = JOptionPane.showInputDialog("Novo sexo (M/F/Outro):", aluno.getSexo());
        aluno.setSexo(novoSexo);

        String novoCursoSigla = JOptionPane.showInputDialog("Novo curso (sigla):", aluno.getCurso().getSigla());
        Curso novoCurso = cursoDAO.findBySigla(novoCursoSigla).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        aluno.setCurso(novoCurso);


        alunoDAO.update(aluno);
        JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
    }


    private void addCurso() {
        String nome = nomeCursoField.getText();
        String sigla = siglaCursoField.getText();
        Curso.Area area = (Curso.Area) areaCursoComboBox.getSelectedItem();

        Curso curso = new Curso(null, nome, sigla, area);
        cursoDAO.create(curso);
        JOptionPane.showMessageDialog(null, "Curso adicionado com sucesso!");

        // Atualizar o combobox de cursos
        populateCursoComboBox();
    }

    private void listCursos() {
        List<Curso> cursos = cursoDAO.findAll();
        StringBuilder builder = new StringBuilder();
        for (Curso curso : cursos) {
            builder.append(curso.getNome()).append(" - ").append(curso.getSigla()).append("\n");
        }
        displayCursosArea.setText(builder.toString());
    }

    private void deleteCurso() {
        String sigla = JOptionPane.showInputDialog("Digite a sigla do curso a ser deletado:");
        Curso curso = cursoDAO.findBySigla(sigla).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        cursoDAO.delete(curso);
        JOptionPane.showMessageDialog(null, "Curso deletado com sucesso!");

        // Atualizar o combobox de cursos
        populateCursoComboBox();
    }

    private void updateCurso() {
        String sigla = JOptionPane.showInputDialog("Digite a sigla do curso:");
        Curso curso = cursoDAO.findBySigla(sigla).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        String novoNome = JOptionPane.showInputDialog("Novo nome:", curso.getNome());
        curso.setNome(novoNome);

        cursoDAO.update(curso);
        JOptionPane.showMessageDialog(null, "Curso atualizado com sucesso!");
    }

}
