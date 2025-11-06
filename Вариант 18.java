import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> graph;
    private int time;

    public Graph() {
        this.graph = new HashMap<>();
        this.time = 0;
    }
    
    public void addEdge(int u, int v) {
        /** Добавление ребра от вершины u к вершине v */
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }
    
    private void dfsUtil(int v, Map<Integer, Boolean> visited, 
                        Map<Integer, Integer> entryTime, 
                        Map<Integer, Integer> exitTime) {
        // Увеличиваем время и записываем время входа
        time++;
        entryTime.put(v, time);
        visited.put(v, true);
        System.out.println("Вход в вершину " + v + ", время входа: " + entryTime.get(v));
        
        // Рекурсивно посещаем все смежные вершины
        List<Integer> neighbors = graph.getOrDefault(v, new ArrayList<>());
        for (int neighbor : neighbors) {
            if (!visited.getOrDefault(neighbor, false)) {
                dfsUtil(neighbor, visited, entryTime, exitTime);
            }
        }
        
        // Увеличиваем время и записываем время выхода
        time++;
        exitTime.put(v, time);
        System.out.println("Выход из вершины " + v + ", время выхода: " + exitTime.get(v));
    }
    
    public DFSResult dfs() {
        return dfs(-1);
    }
    
    public DFSResult dfs(int startVertex) {
        /** Основная функция DFS */
        // Инициализация массивов
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> entryTime = new HashMap<>();
        Map<Integer, Integer> exitTime = new HashMap<>();
        time = 0;
        
        // Если стартовая вершина не указана, берем первую из графа
        if (startVertex == -1) {
            if (!graph.isEmpty()) {
                startVertex = graph.keySet().iterator().next();
            } else {
                startVertex = 0;
            }
        }
        
        System.out.println("Начинаем обход в глубину (DFS):");
        System.out.println("----------------------------------------");
        
        // Запускаем DFS из стартовой вершины
        if (!visited.getOrDefault(startVertex, false)) {
            dfsUtil(startVertex, visited, entryTime, exitTime);
        }
        
        // Проверяем все вершины (для несвязных графов)
        for (int vertex : graph.keySet()) {
            if (!visited.getOrDefault(vertex, false)) {
                dfsUtil(vertex, visited, entryTime, exitTime);
            }
        }
        
        System.out.println("----------------------------------------");
        System.out.println("Итоговые времена входа и выхода:");
        List<Integer> sortedVertices = new ArrayList<>(entryTime.keySet());
        Collections.sort(sortedVertices);
        for (int vertex : sortedVertices) {
            System.out.println("Вершина " + vertex + ": вход=" + entryTime.get(vertex) 
                    + ", выход=" + exitTime.get(vertex));
        }
        
        return new DFSResult(entryTime, exitTime);
    }
    
    // Вспомогательный класс для возврата двух значений
    public static class DFSResult {
        public final Map<Integer, Integer> entryTime;
        public final Map<Integer, Integer> exitTime;
        
        public DFSResult(Map<Integer, Integer> entryTime, Map<Integer, Integer> exitTime) {
            this.entryTime = entryTime;
            this.exitTime = exitTime;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Создаем граф
        Graph g = new Graph();
        
        // Добавляем ребра
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
        
        // Выполняем DFS
        Graph.DFSResult result1 = g.dfs(2);
        
        System.out.println("\n==================================================");
        
        // Другой пример с несвязным графом
        System.out.println("\nПример с несвязным графом:");
        Graph g2 = new Graph();
        g2.addEdge(1, 2);
        g2.addEdge(1, 3);
        g2.addEdge(2, 4);
        g2.addEdge(5, 6);  // Отдельная компонента связности
        
        Graph.DFSResult result2 = g2.dfs(1);
    }
}