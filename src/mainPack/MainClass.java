/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class MainClass {
    
    static String INPUT_PATH;
    public static Random rand = null;
    static int MAX_SIZE = 20;
    static int MIN_SIZE = 5;
    static int POPULATION_SIZE = 1000;
    static double CROSSOVER_PROB = 0.9;
    static double MUTATION_PROB = 0.1;
    static int MAX_GENERATIONS = 300;
    static boolean ELITISM = true;
    static int TOURNAMENT_SIZE = 10;
    static double REMOVE_MUTATION_PROB = 0.3;
    static double ADD_MUTATION_PROB = 0.3;
    static double CHANGE_MUTATION_PROB = 0.4;
    static Cube CUBE;

    public static void main(String[] args) throws IOException {
        //Loop de experimentos
        for (int i = 0; i < 30; i++) {
            rand = new Random(i);
            CUBE = new Cube(INPUT_PATH);
            
            //Gera a população inicial totalmente aleatória
            ArrayList<Individual> newPopulation = generateInitialPopulation(CUBE);
            calculateFitnessForPopulation(newPopulation, CUBE);
            
            //Loop de gerações
            for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
                //Gera nova populacao, cruzando e mutando a anterior
                newPopulation = generateNewPopulation(newPopulation);
                calculateFitnessForPopulation(newPopulation, CUBE);
                
                //Impressão de estatisticas
                System.out.print(gen + ",");
                printPopulationStdVar(newPopulation);
                printBestIndividual(newPopulation);
                printWrostIndividual(newPopulation);
                System.out.print("," + getNumberOfEqualIndividuals(newPopulation) + "\n");
            }
            System.out.println("EXECUCAO " + i);
        }
    }

    public static double calculateFitness(Individual ind, Cube cube) {
        double fitness = 0;
        double greatestFitness = 0;
        int f=0;
                
        Cube cubeCopy = new Cube(cube);
        //Loop para variar as orientações
        for (int orientation = 0; orientation < 4; orientation++) {
            for (int j = 0; j < orientation; j++) {
                cubeCopy.rotateClockwise(j);
            }
            //Loop para variar a face frontal
            for (int front = 0; front < 6; front++) {
                fitness = 0;
                //Realizando os movimentos no cubo
                for (int i = 0; i < ind.getGenes().size(); i++) {
                    cubeCopy.makeMovement(ind.getGenes().get(i), front);
                }
                
                //Calculo da fitness
                fitness += cubeCopy.getNumberOfCorrectFacelets();
                fitness += cubeCopy.getNumberOfCorrectCorners() * 6;
                fitness += cubeCopy.getNumberOfCorrectEdges() * 4;
                
                //A maior fitness entre as orientacoes e faces possiveis é a que vale
                if (fitness > greatestFitness) {
                    f = front;
                    greatestFitness = fitness;
                }
                cubeCopy = new Cube(cube);
            }
        }
        
        ind.setChangePoint(f);
        ind.setFitness(greatestFitness);
        return greatestFitness;
    }

    public static void calculateFitnessForPopulation(ArrayList<Individual> population, Cube cube) {
        for (Individual ind : population) {
            calculateFitness(ind, cube);
        }
    }

    public static ArrayList<Individual> generateInitialPopulation(Cube cube) {
        ArrayList<Individual> population = new ArrayList();
        Individual ind;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            ind = Individual.genenerateRandomIndivual(MAX_SIZE, MIN_SIZE);
            calculateFitness(ind, cube);
            population.add(ind);
        }

        return population;
    }

    public static ArrayList<Individual> generateNewPopulation(ArrayList<Individual> oldPopulation) {
        ArrayList<Individual> newPopulation = new ArrayList();
        ArrayList<Individual> children = new ArrayList();
        double operation;
        Individual ind1, ind2;

        //Ordena a população pela fitness
        oldPopulation.sort((Individual i1, Individual i2) -> {
            return Double.compare(i2.getFitness(), i1.getFitness());
        });
        
        //Adiciona o Elite
        if (ELITISM) {
            ind1 = new Individual(oldPopulation.get(0),true,true,true);
            newPopulation.add(ind1);
        }
        
        //Loop para completar a nova população
        while (newPopulation.size() < POPULATION_SIZE) {
            
            //Seleção
            ind1 = tournament(oldPopulation);
            ind2 = tournament(oldPopulation);
            //Garante que os indivíduos não são iguais
            while (Objects.equals(ind1.getId(), ind2.getId())) {
                ind2 = tournament(oldPopulation);
            }
            
            //Cruzamento
            operation = rand.nextDouble();
            if (operation < CROSSOVER_PROB) {
                children = performCrossover(ind1, ind2);
                ind1 = children.get(0);
                ind2 = children.get(1);
            }
            
            //Mutação
            newPopulation.add(mutateIndividual(ind1));
            
            if (newPopulation.size() < POPULATION_SIZE) {
                newPopulation.add(mutateIndividual(ind2));
            }
        }

        return newPopulation;
    }
    
    //Mutação
    private static Individual mutateIndividual(Individual originalInd) {
        double mutationOp;
        double mutate;
        boolean changeId = false;
        Individual newInd = new Individual(originalInd, true, false, true);
        String newGene;
        int size = newInd.getGenes().size();
        
        //Loop para percorrer os genes do indivíduo
        for (int i=0; i<size; i++) {
            mutate = rand.nextDouble();
            if (mutate < MUTATION_PROB) {
                mutationOp = rand.nextDouble();
                
                //Mutação de adição de gene
                if (mutationOp >= 0 && mutationOp <= ADD_MUTATION_PROB) {
                    if(size<MAX_SIZE){
                        newGene = getRandomGene();
                        newInd.getGenes().add(i, newGene);
                        i++;
                        size++;
                    }
                } 
                //Mutaçao de remoção de gene
                else if (mutationOp > ADD_MUTATION_PROB && mutationOp <= ADD_MUTATION_PROB + REMOVE_MUTATION_PROB) {
                    if(size>MIN_SIZE){
                        newInd.getGenes().remove(i);
                        i--;
                        size--;
                    }
                } 
                //Mutação de mudança de gene
                else {//CHANGE_MUTATION
                    newGene = getRandomGene();
                    
                    while (newInd.getGenes().get(i).equals(newGene)) {
                        newGene = getRandomGene();
                    }
                    newInd.getGenes().set(i, newGene);
                }
                changeId = true;
            }
        }

        if(changeId) newInd.changeId();
        return newInd;
    }
    
    //Seleção
    private static Individual tournament(ArrayList<Individual> population) {
        Individual selected = null;
        ArrayList<Individual> arena = new ArrayList();
        int indexInd;
        double fitness = -0.1;

        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            indexInd = rand.nextInt(population.size() - i);
            arena.add(population.get(indexInd));
            population.remove(indexInd);
        }

        for (Individual ind : arena) {
            population.add(ind);
            if (ind.getFitness() > fitness) {
                selected = new Individual(ind, true, true, true);
                fitness = ind.getFitness();
            }
        }
        return selected;
    }

    //Cruzamento
    private static ArrayList<Individual> performCrossover(Individual parent1, Individual parent2) {
        int startPoint, endPoint;
        Individual smallerParent, biggerParent;
        Individual child1 = new Individual();
        Individual child2 = new Individual();
        ArrayList<Individual> children = new ArrayList();

        if (parent1.getGenes().size() <= parent2.getGenes().size()) {
            smallerParent = parent1;
            biggerParent = parent2;
        } else {
            smallerParent = parent2;
            biggerParent = parent1;
        }

        if (smallerParent.getGenes().size() - 2  > 0) {
            endPoint = rand.nextInt(smallerParent.getGenes().size() - 2) + 2;
            startPoint = rand.nextInt(endPoint - 1) + 1;

            for (int i = 0; i < smallerParent.getGenes().size(); i++) {
                if (i > startPoint && i < endPoint) {
                    child1.getGenes().add(biggerParent.getGenes().get(i));
                    child2.getGenes().add(smallerParent.getGenes().get(i));
                } else {
                    child1.getGenes().add(smallerParent.getGenes().get(i));
                    child2.getGenes().add(biggerParent.getGenes().get(i));
                }
            }

            for (int i = smallerParent.getGenes().size(); i < biggerParent.getGenes().size(); i++) {
                child2.getGenes().add(biggerParent.getGenes().get(i));
            }
            
            children.add(child1);
            children.add(child2);
        } else {
            children.add(smallerParent);
            children.add(biggerParent);
        }

        return children;
    }
    
    private static void printPopulation(ArrayList<Individual> population){
        for(Individual ind: population){
            System.out.println(ind);
        }
    }
    
    private static void printBestIndividual(ArrayList<Individual> population){
        population.sort((Individual i1, Individual i2) -> {
            return Double.compare(i2.getFitness(), i1.getFitness());
        });
        System.out.print("," + population.get(0).getFitness());
    }
    
    private static void printWrostIndividual(ArrayList<Individual> population){
        population.sort((Individual i1, Individual i2) -> {
            return Double.compare(i2.getFitness(), i1.getFitness());
        });
        System.out.print("," + population.get(population.size()-1).getFitness());
    }
    
    private static double getPopulationMean(ArrayList<Individual> population){
        double mean = 0;
        for(Individual ind:population){
            mean += ind.getFitness();
        }
        mean = mean/POPULATION_SIZE;
        return mean;
    }
    
    private static void printPopulationStdVar(ArrayList<Individual> population){
        double mean = getPopulationMean(population);
        double temp = 0;
        for(Individual ind:population){
            temp += (ind.getFitness()-mean)*(ind.getFitness()-mean);
        }
        System.out.print(mean + "," + Math.sqrt(temp/POPULATION_SIZE));
    }
    
    private static int getNumberOfEqualIndividuals(ArrayList<Individual> population){
        ArrayList<Boolean> marks = new ArrayList(Arrays.asList(new Boolean[POPULATION_SIZE]));
        int copies = 0;
        
        Collections.fill(marks, Boolean.FALSE);
        
        for(int i=0; i<POPULATION_SIZE; i++){
            for (int j = i+1; j < POPULATION_SIZE; j++) {
                if(population.get(i).getGenesAsString().equals(population.get(j).getGenesAsString()) && !marks.get(j)){
                    marks.set(j, true);
                    copies++;
                }
            }
        }
        return copies;
    }
    
    private static String getRandomGene() {
        String gene = "";
        int geneNumber = rand.nextInt(12);
        
        return String.valueOf(geneNumber);
    }

}
