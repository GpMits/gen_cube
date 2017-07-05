package mainPack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Cube {

    protected final int size;
    protected final String configuration[][][];//Face (0-Front, 1-Left, 2-Right, 3-Back, 4-Up, 5-Down), Linha, Coluna
    
    //Constrói o objeto cubo a partir de um cubo prévio
    public Cube(Cube copyCube) {
        this.size = copyCube.size;
        this.configuration = new String[6][size][size];
        for (int face = 0; face < 6; face++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    this.configuration[face][i][j] = copyCube.configuration[face][i][j];
                }
            }
        }
    }
    
    //Constrói o objeto cubo a partir de um arquivo de entrada
    public Cube(String path) throws IOException {
        String content = "";
        String contentSplitted[];
        String values[];
        int face;
        content = new String(Files.readAllBytes(Paths.get(path)));
        contentSplitted = content.split("\n");
        size = Integer.parseInt(contentSplitted[0]);
        configuration = new String[6][size][size];
        face = 0;
        for (int i = 1; i < contentSplitted.length;) {
            values = contentSplitted[i].split(" ");
            if (values.length == 1) {
                i++;
                for (int j = 0; j < size; j++) {
                    values = contentSplitted[i].split(" ");
                    for (int k = 0; k < size; k++) {
                        configuration[face][j][k] = values[k];
                    }
                    i++;
                }
                face++;
            }
        }
    }
    
    //Imprime o cubo na entrada padrao
    public void printCube() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    System.out.print(configuration[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    //Re-Orienta o cubo girando a face da frente em 90 graus sentido horário
    public void rotateClockwise(int front){
        makeMovementF(front);
        makeMovementBi(front);
        String aux[] = new String[3];
        switch (front) {
            case 0:
                aux[0] = configuration[1][0][1];
                aux[1] = configuration[1][1][1];
                aux[2] = configuration[1][2][1];
                configuration[1][0][1] = configuration[5][1][0];
                configuration[1][1][1] = configuration[5][1][1];
                configuration[1][2][1] = configuration[5][1][2];
                configuration[5][1][0] = configuration[2][2][1];
                configuration[5][1][1] = configuration[2][1][1];
                configuration[5][1][2] = configuration[2][0][1];
                configuration[2][0][1] = configuration[4][1][0];
                configuration[2][1][1] = configuration[4][1][1];
                configuration[2][2][1] = configuration[4][1][2];
                configuration[4][1][0] = aux[0];
                configuration[4][1][1] = aux[1];
                configuration[4][1][2] = aux[2];
                break;
            case 1:
                aux[0] = configuration[3][0][1];
                aux[1] = configuration[3][1][1];
                aux[2] = configuration[3][2][1];
                configuration[3][0][1] = configuration[5][2][1];
                configuration[3][1][1] = configuration[5][1][1];
                configuration[3][2][1] = configuration[5][0][1];
                configuration[5][0][1] = configuration[0][0][1];
                configuration[5][1][1] = configuration[0][1][1];
                configuration[5][2][1] = configuration[0][2][1];
                configuration[0][0][1] = configuration[4][0][1];
                configuration[0][1][1] = configuration[4][1][1];
                configuration[0][2][1] = configuration[4][2][1];
                configuration[4][0][1] = aux[2];
                configuration[4][1][1] = aux[1];
                configuration[4][2][1] = aux[0];
                break;
            case 2:
                aux[0] = configuration[0][0][1];
                aux[1] = configuration[0][1][1];
                aux[2] = configuration[0][2][1];
                configuration[0][0][1] = configuration[5][0][1];
                configuration[0][1][1] = configuration[5][1][1];
                configuration[0][2][1] = configuration[5][2][1];
                configuration[5][0][1] = configuration[3][2][1];
                configuration[5][1][1] = configuration[3][1][1];
                configuration[5][2][1] = configuration[3][0][1];
                configuration[3][0][1] = configuration[4][2][1];
                configuration[3][1][1] = configuration[4][1][1];
                configuration[3][2][1] = configuration[4][0][1];
                configuration[4][0][1] = aux[0];
                configuration[4][1][1] = aux[1];
                configuration[4][2][1] = aux[2];
                break;
            case 3:
                aux[0] = configuration[2][0][1];
                aux[1] = configuration[2][1][1];
                aux[2] = configuration[2][2][1];
                configuration[2][0][1] = configuration[5][1][2];
                configuration[2][1][1] = configuration[5][1][1];
                configuration[2][2][1] = configuration[5][1][0];
                configuration[5][1][0] = configuration[1][0][1];
                configuration[5][1][1] = configuration[1][1][1];
                configuration[5][1][2] = configuration[1][2][1];
                configuration[1][0][1] = configuration[4][1][2];
                configuration[1][1][1] = configuration[4][1][1];
                configuration[1][2][1] = configuration[4][1][0];
                configuration[4][1][0] = aux[0];
                configuration[4][1][1] = aux[1];
                configuration[4][1][2] = aux[2];
                break;
            case 4:
                aux[0] = configuration[1][1][0];
                aux[1] = configuration[1][1][1];
                aux[2] = configuration[1][1][2];
                configuration[1][1][0] = configuration[0][1][0];
                configuration[1][1][1] = configuration[0][1][1];
                configuration[1][1][2] = configuration[0][1][2];
                configuration[0][1][0] = configuration[2][1][0];
                configuration[0][1][1] = configuration[2][1][1];
                configuration[0][1][2] = configuration[2][1][2];
                configuration[2][1][0] = configuration[3][1][0];
                configuration[2][1][1] = configuration[3][1][1];
                configuration[2][1][2] = configuration[3][1][2];
                configuration[3][1][0] = aux[0];
                configuration[3][1][1] = aux[1];
                configuration[3][1][2] = aux[2];
                break;
            case 5:
                aux[0] = configuration[1][1][0];
                aux[1] = configuration[1][1][1];
                aux[2] = configuration[1][1][2];
                configuration[1][1][0] = configuration[3][1][0];
                configuration[1][1][1] = configuration[3][1][1];
                configuration[1][1][2] = configuration[3][1][2];
                configuration[3][1][0] = configuration[2][1][0];
                configuration[3][1][1] = configuration[2][1][1];
                configuration[3][1][2] = configuration[2][1][2];
                configuration[2][1][0] = configuration[0][1][0];
                configuration[2][1][1] = configuration[0][1][1];
                configuration[2][1][2] = configuration[0][1][2];
                configuration[0][1][0] = aux[0];
                configuration[0][1][1] = aux[1];
                configuration[0][1][2] = aux[2];
                break;
            default:
                break;
        }
        
    }
    
    //Realiza a tradução de um gene em uma sequencia de movimentos
    //Varia conforme qual face é selecionada como frente
    public void makeMovement(String gene, int front) {
        switch (gene) {
            case "1":
                makeMovementF(front);
                makeMovementR(front);
                makeMovementB(front);
                makeMovementL(front);
                makeMovementU(front);
                makeMovementLi(front);
                makeMovementU(front);
                makeMovementBi(front);
                makeMovementRi(front);
                makeMovementFi(front);
                makeMovementLi(front);
                makeMovementUi(front);
                makeMovementL(front);
                makeMovementUi(front);
                break;
            case "2":
                makeMovementFi(front);
                makeMovementLi(front);
                makeMovementBi(front);
                makeMovementRi(front);
                makeMovementUi(front);
                makeMovementR(front);
                makeMovementUi(front);
                makeMovementB(front);
                makeMovementL(front);
                makeMovementF(front);
                makeMovementR(front);
                makeMovementU(front);
                makeMovementRi(front);
                makeMovementU(front);
                break;
            case "3":
                makeMovementL(front);
                makeMovementDi(front);
                makeMovementLi(front);
                makeMovementFi(front);
                makeMovementDi(front);
                makeMovementF(front);
                makeMovementU(front);
                makeMovementFi(front);
                makeMovementD(front);
                makeMovementF(front);
                makeMovementL(front);
                makeMovementD(front);
                makeMovementLi(front);
                makeMovementUi(front);
                break;
            case "4":
                makeMovementRi(front);
                makeMovementD(front);
                makeMovementR(front);
                makeMovementF(front);
                makeMovementD(front);
                makeMovementFi(front);
                makeMovementUi(front);
                makeMovementF(front);
                makeMovementDi(front);
                makeMovementFi(front);
                makeMovementRi(front);
                makeMovementDi(front);
                makeMovementR(front);
                makeMovementU(front);
                break;
            case "5":
                makeMovementU(front);
                makeMovementF2(front);
                makeMovementUi(front);
                makeMovementRi(front);
                makeMovementDi(front);
                makeMovementLi(front);
                makeMovementF2(front);
                makeMovementL(front);
                makeMovementD(front);
                makeMovementR(front);
                break;
            case "6":
                makeMovementUi(front);
                makeMovementF2(front);
                makeMovementU(front);
                makeMovementL(front);
                makeMovementD(front);
                makeMovementR(front);
                makeMovementF2(front);
                makeMovementRi(front);
                makeMovementDi(front);
                makeMovementLi(front);
                break;
            case "7":
                makeMovementRi(front);
                makeMovementU(front);
                makeMovementR(front);
                makeMovementUi(front);
                makeMovementRi(front);
                makeMovementU(front);
                makeMovementF(front);
                makeMovementR(front);
                makeMovementBi(front);
                makeMovementR(front);
                makeMovementB(front);
                makeMovementR(front);
                makeMovementFi(front);
                makeMovementR2(front);
                break;
            case "8":
                makeMovementL(front);
                makeMovementUi(front);
                makeMovementLi(front);
                makeMovementU(front);
                makeMovementL(front);
                makeMovementUi(front);
                makeMovementFi(front);
                makeMovementLi(front);
                makeMovementB(front);
                makeMovementLi(front);
                makeMovementBi(front);
                makeMovementLi(front);
                makeMovementF(front);
                makeMovementL2(front);
                break;
            case "9":
                makeMovementFi(front);
                makeMovementU(front);
                makeMovementB(front);
                makeMovementUi(front);
                makeMovementF(front);
                makeMovementU(front);
                makeMovementBi(front);
                makeMovementUi(front);
                break;
            case "10":
                makeMovementF(front);
                makeMovementUi(front);
                makeMovementBi(front);
                makeMovementU(front);
                makeMovementFi(front);
                makeMovementUi(front);
                makeMovementB(front);
                makeMovementU(front);
                break;
            case "11":
                makeMovementR(front);
                makeMovementLi(front);
                makeMovementU2(front);
                makeMovementRi(front);
                makeMovementL(front);
                makeMovementF2(front);
                break;
            case "12":
                makeMovementLi(front);
                makeMovementR(front);
                makeMovementU2(front);
                makeMovementL(front);
                makeMovementRi(front);
                makeMovementF2(front);
                break;
            default:
                break;
        }
    }
    
    //Gira a face 0 em 90 graus sentido horario
    private void makeMovementF() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(0);
        dimensionAux[0] = configuration[1][0][2];
        dimensionAux[1] = configuration[1][1][2];
        dimensionAux[2] = configuration[1][2][2];
        dimensionAux2[0] = configuration[4][2][0];
        dimensionAux2[1] = configuration[4][2][1];
        dimensionAux2[2] = configuration[4][2][2];
        configuration[1][0][2] = configuration[5][0][0];
        configuration[1][1][2] = configuration[5][0][1];
        configuration[1][2][2] = configuration[5][0][2];
        configuration[4][2][0] = dimensionAux[2];
        configuration[4][2][1] = dimensionAux[1];
        configuration[4][2][2] = dimensionAux[0];
        dimensionAux[0] = configuration[2][0][0];
        dimensionAux[1] = configuration[2][1][0];
        dimensionAux[2] = configuration[2][2][0];
        configuration[2][0][0] = dimensionAux2[0];
        configuration[2][1][0] = dimensionAux2[1];
        configuration[2][2][0] = dimensionAux2[2];
        configuration[5][0][0] = dimensionAux[2];
        configuration[5][0][1] = dimensionAux[1];
        configuration[5][0][2] = dimensionAux[0];
    }

    //Gira a face 3 em 90 graus sentido horario
    public void makeMovementB() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(3);
        dimensionAux[0] = configuration[2][0][2];
        dimensionAux[1] = configuration[2][1][2];
        dimensionAux[2] = configuration[2][2][2];
        dimensionAux2[0] = configuration[4][0][2];
        dimensionAux2[1] = configuration[4][0][1];
        dimensionAux2[2] = configuration[4][0][0];
        configuration[2][0][2] = configuration[5][2][2];
        configuration[2][1][2] = configuration[5][2][1];
        configuration[2][2][2] = configuration[5][2][0];
        configuration[4][0][2] = dimensionAux[2];
        configuration[4][0][1] = dimensionAux[1];
        configuration[4][0][0] = dimensionAux[0];
        dimensionAux[0] = configuration[1][0][0];
        dimensionAux[1] = configuration[1][1][0];
        dimensionAux[2] = configuration[1][2][0];
        configuration[1][0][0] = dimensionAux2[0];
        configuration[1][1][0] = dimensionAux2[1];
        configuration[1][2][0] = dimensionAux2[2];
        configuration[5][2][2] = dimensionAux[2];
        configuration[5][2][1] = dimensionAux[1];
        configuration[5][2][0] = dimensionAux[0];
    }
    
    //Gira a face 4 em 90 graus sentido horario
    private void makeMovementU() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(4);
        dimensionAux[0] = configuration[1][0][0];
        dimensionAux[1] = configuration[1][0][1];
        dimensionAux[2] = configuration[1][0][2];
        dimensionAux2[0] = configuration[3][0][2];
        dimensionAux2[1] = configuration[3][0][1];
        dimensionAux2[2] = configuration[3][0][0];
        configuration[1][0][0] = configuration[0][0][0];
        configuration[1][0][1] = configuration[0][0][1];
        configuration[1][0][2] = configuration[0][0][2];
        configuration[3][0][2] = dimensionAux[2];
        configuration[3][0][1] = dimensionAux[1];
        configuration[3][0][0] = dimensionAux[0];
        dimensionAux[0] = configuration[2][0][2];
        dimensionAux[1] = configuration[2][0][1];
        dimensionAux[2] = configuration[2][0][0];
        configuration[2][0][2] = dimensionAux2[0];
        configuration[2][0][1] = dimensionAux2[1];
        configuration[2][0][0] = dimensionAux2[2];
        configuration[0][0][0] = dimensionAux[2];
        configuration[0][0][1] = dimensionAux[1];
        configuration[0][0][2] = dimensionAux[0];
    }

    //Gira a face 5 em 90 graus sentido horario
    private void makeMovementD() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(5);
        dimensionAux[0] = configuration[1][2][2];
        dimensionAux[1] = configuration[1][2][1];
        dimensionAux[2] = configuration[1][2][0];
        dimensionAux2[0] = configuration[0][2][0];
        dimensionAux2[1] = configuration[0][2][1];
        dimensionAux2[2] = configuration[0][2][2];
        configuration[1][2][2] = configuration[3][2][2];
        configuration[1][2][1] = configuration[3][2][1];
        configuration[1][2][0] = configuration[3][2][0];
        configuration[0][2][0] = dimensionAux[2];
        configuration[0][2][1] = dimensionAux[1];
        configuration[0][2][2] = dimensionAux[0];
        dimensionAux[0] = configuration[2][2][0];
        dimensionAux[1] = configuration[2][2][1];
        dimensionAux[2] = configuration[2][2][2];
        configuration[2][2][0] = dimensionAux2[0];
        configuration[2][2][1] = dimensionAux2[1];
        configuration[2][2][2] = dimensionAux2[2];
        configuration[3][2][2] = dimensionAux[2];
        configuration[3][2][1] = dimensionAux[1];
        configuration[3][2][0] = dimensionAux[0];
    }
    
    //Gira a face 1 em 90 graus sentido horario
    private void makeMovementL() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(1);
        dimensionAux[0] = configuration[3][0][2];
        dimensionAux[1] = configuration[3][1][2];
        dimensionAux[2] = configuration[3][2][2];
        dimensionAux2[0] = configuration[4][0][0];
        dimensionAux2[1] = configuration[4][1][0];
        dimensionAux2[2] = configuration[4][2][0];
        configuration[3][0][2] = configuration[5][2][0];
        configuration[3][1][2] = configuration[5][1][0];
        configuration[3][2][2] = configuration[5][0][0];
        configuration[4][0][0] = dimensionAux[2];
        configuration[4][1][0] = dimensionAux[1];
        configuration[4][2][0] = dimensionAux[0];
        dimensionAux[0] = configuration[0][0][0];
        dimensionAux[1] = configuration[0][1][0];
        dimensionAux[2] = configuration[0][2][0];
        configuration[0][0][0] = dimensionAux2[0];
        configuration[0][1][0] = dimensionAux2[1];
        configuration[0][2][0] = dimensionAux2[2];
        configuration[5][0][0] = dimensionAux[2];
        configuration[5][1][0] = dimensionAux[1];
        configuration[5][2][0] = dimensionAux[0];
    }
    
    //Gira a face 2 em 90 graus sentido horario
    public void makeMovementR() {
        String dimensionAux[] = new String[3];
        String dimensionAux2[] = new String[3];
        rotateFaceClockwise(2);
        dimensionAux[0] = configuration[0][0][2];
        dimensionAux[1] = configuration[0][1][2];
        dimensionAux[2] = configuration[0][2][2];
        dimensionAux2[0] = configuration[4][2][2];
        dimensionAux2[1] = configuration[4][1][2];
        dimensionAux2[2] = configuration[4][0][2];
        configuration[0][0][2] = configuration[5][0][2];
        configuration[0][1][2] = configuration[5][1][2];
        configuration[0][2][2] = configuration[5][2][2];
        configuration[4][0][2] = dimensionAux[0];
        configuration[4][1][2] = dimensionAux[1];
        configuration[4][2][2] = dimensionAux[2];
        dimensionAux[0] = configuration[3][0][0];
        dimensionAux[1] = configuration[3][1][0];
        dimensionAux[2] = configuration[3][2][0];
        configuration[3][0][0] = dimensionAux2[0];
        configuration[3][1][0] = dimensionAux2[1];
        configuration[3][2][0] = dimensionAux2[2];
        configuration[5][0][2] = dimensionAux[2];
        configuration[5][1][2] = dimensionAux[1];
        configuration[5][2][2] = dimensionAux[0];
    }
    
    //Gira a face 0 em 90 graus sentido anti horario
    public void makeMovementFi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(0);
        dimensionAux[0] = configuration[1][0][2];
        dimensionAux[1] = configuration[1][1][2];
        dimensionAux[2] = configuration[1][2][2];
        configuration[1][0][2] = configuration[4][2][2];
        configuration[1][1][2] = configuration[4][2][1];
        configuration[1][2][2] = configuration[4][2][0];
        configuration[4][2][0] = configuration[2][0][0];
        configuration[4][2][1] = configuration[2][1][0];
        configuration[4][2][2] = configuration[2][2][0];
        configuration[2][0][0] = configuration[5][0][2];
        configuration[2][1][0] = configuration[5][0][1];
        configuration[2][2][0] = configuration[5][0][0];
        configuration[5][0][0] = dimensionAux[0];
        configuration[5][0][1] = dimensionAux[1];
        configuration[5][0][2] = dimensionAux[2];
    }
    
    //Gira a face 3 em 90 graus sentido anti horario
    public void makeMovementBi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(3);
        dimensionAux[0] = configuration[2][0][2];
        dimensionAux[1] = configuration[2][1][2];
        dimensionAux[2] = configuration[2][2][2];
        configuration[2][0][2] = configuration[4][0][0];
        configuration[2][1][2] = configuration[4][0][1];
        configuration[2][2][2] = configuration[4][0][2];
        configuration[4][0][2] = configuration[1][0][0];
        configuration[4][0][1] = configuration[1][1][0];
        configuration[4][0][0] = configuration[1][2][0];
        configuration[1][0][0] = configuration[5][2][0];
        configuration[1][1][0] = configuration[5][2][1];
        configuration[1][2][0] = configuration[5][2][2];
        configuration[5][2][2] = dimensionAux[0];
        configuration[5][2][1] = dimensionAux[1];
        configuration[5][2][0] = dimensionAux[2];
    }
    
    //Gira a face 4 em 90 graus sentido anti horario
    public void makeMovementDi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(5);
        dimensionAux[0] = configuration[1][2][0];
        dimensionAux[1] = configuration[1][2][1];
        dimensionAux[2] = configuration[1][2][2];
        configuration[1][2][0] = configuration[0][2][0];
        configuration[1][2][1] = configuration[0][2][1];
        configuration[1][2][2] = configuration[0][2][2];
        configuration[0][2][0] = configuration[2][2][0];
        configuration[0][2][1] = configuration[2][2][1];
        configuration[0][2][2] = configuration[2][2][2];
        configuration[2][2][0] = configuration[3][2][0];
        configuration[2][2][1] = configuration[3][2][1];
        configuration[2][2][2] = configuration[3][2][2];
        configuration[3][2][0] = dimensionAux[0];
        configuration[3][2][1] = dimensionAux[1];
        configuration[3][2][2] = dimensionAux[2];
    }
    
    //Gira a face 5 em 90 graus sentido anti horario
    public void makeMovementUi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(4);
        dimensionAux[0] = configuration[1][0][0];
        dimensionAux[1] = configuration[1][0][1];
        dimensionAux[2] = configuration[1][0][2];
        configuration[1][0][0] = configuration[3][0][0];
        configuration[1][0][1] = configuration[3][0][1];
        configuration[1][0][2] = configuration[3][0][2];
        configuration[3][0][0] = configuration[2][0][0];
        configuration[3][0][1] = configuration[2][0][1];
        configuration[3][0][2] = configuration[2][0][2];
        configuration[2][0][0] = configuration[0][0][0];
        configuration[2][0][1] = configuration[0][0][1];
        configuration[2][0][2] = configuration[0][0][2];
        configuration[0][0][0] = dimensionAux[0];
        configuration[0][0][1] = dimensionAux[1];
        configuration[0][0][2] = dimensionAux[2];
    }

    //Gira a face 1 em 90 graus sentido anti horario
    public void makeMovementLi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(1);
        dimensionAux[0] = configuration[3][0][2];
        dimensionAux[1] = configuration[3][1][2];
        dimensionAux[2] = configuration[3][2][2];
        configuration[3][0][2] = configuration[4][2][0];
        configuration[3][1][2] = configuration[4][1][0];
        configuration[3][2][2] = configuration[4][0][0];
        configuration[4][0][0] = configuration[0][0][0];
        configuration[4][1][0] = configuration[0][1][0];
        configuration[4][2][0] = configuration[0][2][0];
        configuration[0][0][0] = configuration[5][0][0];
        configuration[0][1][0] = configuration[5][1][0];
        configuration[0][2][0] = configuration[5][2][0];
        configuration[5][0][0] = dimensionAux[2];
        configuration[5][1][0] = dimensionAux[1];
        configuration[5][2][0] = dimensionAux[0];
    }

    //Gira a face 2 em 90 graus sentido anti horario
    public void makeMovementRi() {
        String dimensionAux[] = new String[3];
        rotateFaceCounterClockwise(2);
        dimensionAux[0] = configuration[0][0][2];
        dimensionAux[1] = configuration[0][1][2];
        dimensionAux[2] = configuration[0][2][2];
        configuration[0][0][2] = configuration[4][0][2];
        configuration[0][1][2] = configuration[4][1][2];
        configuration[0][2][2] = configuration[4][2][2];
        configuration[4][0][2] = configuration[3][2][0];
        configuration[4][1][2] = configuration[3][1][0];
        configuration[4][2][2] = configuration[3][0][0];
        configuration[3][0][0] = configuration[5][2][2];
        configuration[3][1][0] = configuration[5][1][2];
        configuration[3][2][0] = configuration[5][0][2];
        configuration[5][0][2] = dimensionAux[0];
        configuration[5][1][2] = dimensionAux[1];
        configuration[5][2][2] = dimensionAux[2];
    }

    //Gira a face 0 em 180 graus sentido horario
    private void makeMovementF2() {
        makeMovementF();
        makeMovementF();
    }
    
    //Gira a face 3 em 180 graus sentido horario
    private void makeMovementB2() {
        makeMovementB();
        makeMovementB();
    }
    
    //Gira a face 4 em 180 graus sentido horario
    private void makeMovementU2() {
        makeMovementU();
        makeMovementU();
    }

    //Gira a face 5 em 180 graus sentido horario
    private void makeMovementD2() {
        makeMovementD();
        makeMovementD();
    }

    //Gira a face 1 em 180 graus sentido horario
    private void makeMovementL2() {
        makeMovementL();
        makeMovementL();
    }

    //Gira a face 2 em 180 graus sentido horario
    private void makeMovementR2() {
        makeMovementR();
        makeMovementR();
    }
    
    //Os métodos sobrescritos abaixo realizam a mesma operação que sua versão sem parâmetro
    //Porem o movimento muda conforme qual face é selecionada como frente
    private void makeMovementF(int front) {
        switch (front) {
            case 0:
                makeMovementF();
                break;
            case 1:
                makeMovementL();
                break;
            case 2:
                makeMovementR();
                break;
            case 3:
                makeMovementB();
                break;
            case 4:
                makeMovementU();
                break;
            case 5:
                makeMovementD();
                break;
            default:
                break;
        }
    }

    private void makeMovementB(int front) {
        switch (front) {
            case 0:
                makeMovementB();
                break;
            case 1:
                makeMovementR();
                break;
            case 2:
                makeMovementL();
                break;
            case 3:
                makeMovementF();
                break;
            case 4:
                makeMovementD();
                break;
            case 5:
                makeMovementU();
                break;
            default:
                break;
        }
    }

    private void makeMovementU(int front) {
        switch (front) {
            case 0:
                makeMovementU();
                break;
            case 1:
                makeMovementU();
                break;
            case 2:
                makeMovementU();
                break;
            case 3:
                makeMovementU();
                break;
            case 4:
                makeMovementB();
                break;
            case 5:
                makeMovementF();
                break;
            default:
                break;
        }
    }

    private void makeMovementD(int front) {
        switch (front) {
            case 0:
                makeMovementD();
                break;
            case 1:
                makeMovementD();
                break;
            case 2:
                makeMovementD();
                break;
            case 3:
                makeMovementD();
                break;
            case 4:
                makeMovementF();
                break;
            case 5:
                makeMovementB();
                break;
            default:
                break;
        }
    }

    private void makeMovementL(int front) {
        switch (front) {
            case 0:
                makeMovementL();
                break;
            case 1:
                makeMovementB();
                break;
            case 2:
                makeMovementF();
                break;
            case 3:
                makeMovementR();
                break;
            case 4:
                makeMovementL();
                break;
            case 5:
                makeMovementL();
                break;
            default:
                break;
        }
    }

    private void makeMovementR(int front) {
        switch (front) {
            case 0:
                makeMovementR();
                break;
            case 1:
                makeMovementF();
                break;
            case 2:
                makeMovementB();
                break;
            case 3:
                makeMovementL();
                break;
            case 4:
                makeMovementR();
                break;
            case 5:
                makeMovementR();
                break;
            default:
                break;
        }
    }

    private void makeMovementFi(int front) {
        switch (front) {
            case 0:
                makeMovementFi();
                break;
            case 1:
                makeMovementLi();
                break;
            case 2:
                makeMovementRi();
                break;
            case 3:
                makeMovementBi();
                break;
            case 4:
                makeMovementUi();
                break;
            case 5:
                makeMovementDi();
                break;
            default:
                break;
        }
    }

    private void makeMovementBi(int front) {
        switch (front) {
            case 0:
                makeMovementBi();
                break;
            case 1:
                makeMovementRi();
                break;
            case 2:
                makeMovementLi();
                break;
            case 3:
                makeMovementFi();
                break;
            case 4:
                makeMovementDi();
                break;
            case 5:
                makeMovementUi();
                break;
            default:
                break;
        }
    }

    private void makeMovementDi(int front) {
        switch (front) {
            case 0:
                makeMovementDi();
                break;
            case 1:
                makeMovementDi();
                break;
            case 2:
                makeMovementDi();
                break;
            case 3:
                makeMovementDi();
                break;
            case 4:
                makeMovementFi();
                break;
            case 5:
                makeMovementBi();
                break;
            default:
                break;
        }
    }

    private void makeMovementUi(int front) {
        switch (front) {
            case 0:
                makeMovementUi();
                break;
            case 1:
                makeMovementUi();
                break;
            case 2:
                makeMovementUi();
                break;
            case 3:
                makeMovementUi();
                break;
            case 4:
                makeMovementBi();
                break;
            case 5:
                makeMovementFi();
                break;
            default:
                break;
        }
    }

    private void makeMovementLi(int front) {
        switch (front) {
            case 0:
                makeMovementLi();
                break;
            case 1:
                makeMovementBi();
                break;
            case 2:
                makeMovementFi();
                break;
            case 3:
                makeMovementRi();
                break;
            case 4:
                makeMovementLi();
                break;
            case 5:
                makeMovementLi();
                break;
            default:
                break;
        }
    }

    private void makeMovementRi(int front) {
        switch (front) {
            case 0:
                makeMovementRi();
                break;
            case 1:
                makeMovementFi();
                break;
            case 2:
                makeMovementBi();
                break;
            case 3:
                makeMovementLi();
                break;
            case 4:
                makeMovementRi();
                break;
            case 5:
                makeMovementRi();
                break;
            default:
                break;
        }
    }

    private void makeMovementF2(int front) {
        switch (front) {
            case 0:
                makeMovementF();
                makeMovementF();
                break;
            case 1:
                makeMovementL();
                makeMovementL();
                break;
            case 2:
                makeMovementR();
                makeMovementR();
                break;
            case 3:
                makeMovementB();
                makeMovementB();
                break;
            case 4:
                makeMovementU();
                makeMovementU();
                break;
            case 5:
                makeMovementD();
                makeMovementD();
                break;
            default:
                break;
        }
    }

    private void makeMovementB2(int front) {
        switch (front) {
            case 0:
                makeMovementB();
                makeMovementB();
                break;
            case 1:
                makeMovementR();
                makeMovementR();
                break;
            case 2:
                makeMovementL();
                makeMovementL();
                break;
            case 3:
                makeMovementF();
                makeMovementF();
                break;
            case 4:
                makeMovementD();
                makeMovementD();
                break;
            case 5:
                makeMovementU();
                makeMovementU();
                break;
            default:
                break;
        }
    }

    private void makeMovementU2(int front) {
        switch (front) {
            case 0:
                makeMovementU();
                makeMovementU();
                break;
            case 1:
                makeMovementU();
                makeMovementU();
                break;
            case 2:
                makeMovementU();
                makeMovementU();
                break;
            case 3:
                makeMovementU();
                makeMovementU();
                break;
            case 4:
                makeMovementB();
                makeMovementB();
                break;
            case 5:
                makeMovementF();
                makeMovementF();
                break;
            default:
                break;
        }
    }

    private void makeMovementD2(int front) {
        switch (front) {
            case 0:
                makeMovementD();
                makeMovementD();
                break;
            case 1:
                makeMovementD();
                makeMovementD();
                break;
            case 2:
                makeMovementD();
                makeMovementD();
                break;
            case 3:
                makeMovementD();
                makeMovementD();
                break;
            case 4:
                makeMovementF();
                makeMovementF();
                break;
            case 5:
                makeMovementB();
                makeMovementB();
                break;
            default:
                break;
        }
    }

    private void makeMovementL2(int front) {
        switch (front) {
            case 0:
                makeMovementL();
                makeMovementL();
                break;
            case 1:
                makeMovementB();
                makeMovementB();
                break;
            case 2:
                makeMovementF();
                makeMovementF();
                break;
            case 3:
                makeMovementR();
                makeMovementR();
                break;
            case 4:
                makeMovementL();
                makeMovementL();
                break;
            case 5:
                makeMovementL();
                makeMovementL();
                break;
            default:
                break;
        }
    }

    private void makeMovementR2(int front) {
        switch (front) {
            case 0:
                makeMovementR();
                makeMovementR();
                break;
            case 1:
                makeMovementF();
                makeMovementF();
                break;
            case 2:
                makeMovementB();
                makeMovementB();
                break;
            case 3:
                makeMovementL();
                makeMovementL();
                break;
            case 4:
                makeMovementR();
                makeMovementR();
                break;
            case 5:
                makeMovementR();
                makeMovementR();
                break;
            default:
                break;
        }
    }
    
    //Gira a somente face em 90 graus sentido horário
    private void rotateFaceClockwise(int face) {
        String faceAux[][] = new String[size][size];
        faceAux[0][0] = configuration[face][2][0];
        faceAux[0][1] = configuration[face][1][0];
        faceAux[0][2] = configuration[face][0][0];
        faceAux[2][0] = configuration[face][2][2];
        faceAux[2][1] = configuration[face][1][2];
        faceAux[2][2] = configuration[face][0][2];
        faceAux[1][0] = configuration[face][2][1];
        faceAux[1][2] = configuration[face][0][1];
        faceAux[1][1] = configuration[face][1][1];
        configuration[face] = faceAux;
    }
    
    //Gira somemte a face em 90 graus sentido anti horário
    private void rotateFaceCounterClockwise(int face) {
        String faceAux[][] = new String[size][size];
        faceAux[0][0] = configuration[face][0][2];
        faceAux[0][1] = configuration[face][1][2];
        faceAux[0][2] = configuration[face][2][2];
        faceAux[2][0] = configuration[face][0][0];
        faceAux[2][1] = configuration[face][1][0];
        faceAux[2][2] = configuration[face][2][0];
        faceAux[1][0] = configuration[face][0][1];
        faceAux[1][2] = configuration[face][2][1];
        faceAux[1][1] = configuration[face][1][1];
        configuration[face] = faceAux;
    }

    //Retorna o número de facelets corretos nas faces dos cubos
    public int getNumberOfCorrectFacelets() {
        int num = 0;
        String correctColor;
        for (int f = 0; f < 6; f++) {
            correctColor = configuration[f][size / 2][size / 2];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (configuration[f][i][j].equals(correctColor)) {
                        num++;
                    }
                }
            }
        }
        return num-6;
    }
    
    //Retorna o número de extremidades centrais corretas nas faces dos cubos
    public int getNumberOfCorrectEdges() {
        int num = 0;

        if(configuration[0][0][1].equals(configuration[0][1][1]) &&
                configuration[4][2][1].equals(configuration[4][1][1])) num ++;
        
        if(configuration[0][1][0].equals(configuration[0][1][1]) &&
                configuration[1][1][2].equals(configuration[1][1][1])) num ++;
        
        if(configuration[0][2][1].equals(configuration[0][1][1]) &&
                configuration[5][0][1].equals(configuration[5][1][1])) num ++;
        
        if(configuration[0][1][2].equals(configuration[0][1][1]) &&
                configuration[2][1][0].equals(configuration[2][1][1])) num ++;
        
        if(configuration[3][0][1].equals(configuration[3][1][1]) &&
                configuration[4][0][1].equals(configuration[4][1][1])) num ++;
        
        if(configuration[3][1][0].equals(configuration[3][1][1]) &&
                configuration[2][1][2].equals(configuration[2][1][1])) num ++;
        
        if(configuration[3][2][1].equals(configuration[3][1][1]) &&
                configuration[5][2][1].equals(configuration[5][1][1])) num ++;
        
        if(configuration[3][1][2].equals(configuration[3][1][1]) &&
                configuration[1][1][0].equals(configuration[1][1][1])) num ++;
        
        if(configuration[1][0][1].equals(configuration[1][1][1]) &&
                configuration[4][1][0].equals(configuration[4][1][1])) num ++;
        
        if(configuration[1][2][1].equals(configuration[1][1][1]) &&
                configuration[5][1][0].equals(configuration[5][1][1])) num ++;
        
        if(configuration[2][0][1].equals(configuration[2][1][1]) &&
                configuration[4][1][2].equals(configuration[4][1][1])) num ++;
        
        if(configuration[2][2][1].equals(configuration[2][1][1]) &&
                configuration[5][1][2].equals(configuration[5][1][1])) num ++;
        
       
        return num;
    }
    
    //Retorna o número de cantos corretos nas faces dos cubos
    public int getNumberOfCorrectCorners() {
        int num  = 0;
        if(configuration[0][0][2].equals(configuration[0][1][1]) &&
                configuration[4][2][2].equals(configuration[4][1][1]) &&
                configuration[2][0][0].equals(configuration[2][1][1])) num ++;
        
        if(configuration[0][0][0].equals(configuration[0][1][1]) &&
                configuration[4][2][0].equals(configuration[4][1][1]) &&
                configuration[1][0][2].equals(configuration[1][1][1])) num ++;
        
        if(configuration[0][2][0].equals(configuration[0][1][1]) &&
                configuration[5][0][0].equals(configuration[5][1][1]) &&
                configuration[1][2][2].equals(configuration[1][1][1])) num ++;
        
        if(configuration[0][2][2].equals(configuration[0][1][1]) &&
                configuration[5][0][2].equals(configuration[5][1][1]) &&
                configuration[2][2][0].equals(configuration[2][1][1])) num ++;
        
        if(configuration[3][0][2].equals(configuration[3][1][1]) &&
                configuration[4][0][0].equals(configuration[4][1][1]) &&
                configuration[1][0][0].equals(configuration[1][1][1])) num ++;
        
        if(configuration[3][0][0].equals(configuration[3][1][1]) &&
                configuration[4][0][2].equals(configuration[4][1][1]) &&
                configuration[2][0][2].equals(configuration[2][1][1])) num ++;
        
        if(configuration[3][2][0].equals(configuration[3][1][1]) &&
                configuration[5][2][2].equals(configuration[5][1][1]) &&
                configuration[2][2][2].equals(configuration[2][1][1])) num ++;
        
        if(configuration[3][2][2].equals(configuration[3][1][1]) &&
                configuration[5][2][0].equals(configuration[5][1][1]) &&
                configuration[1][2][0].equals(configuration[1][1][1])) num ++;
        
       
        return num;
    }

}