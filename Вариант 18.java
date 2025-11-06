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







Начинаем обход в глубину (DFS):
----------------------------------------
Вход в вершину 2, время входа: 1
Вход в вершину 0, время входа: 2
Вход в вершину 1, время входа: 3
Выход из вершины 1, время выхода: 4
Выход из вершины 0, время выхода: 5
Вход в вершину 3, время входа: 6
Выход из вершины 3, время выхода: 7
Выход из вершины 2, время выхода: 8
----------------------------------------
Итоговые времена входа и выхода:
Вершина 0: вход=2, выход=5
Вершина 1: вход=3, выход=4
Вершина 2: вход=1, выход=8
Вершина 3: вход=6, выход=7

==================================================

Пример с несвязным графом:
Начинаем обход в глубину (DFS):
----------------------------------------
Вход в вершину 1, время входа: 1
Вход в вершину 2, время входа: 2
Вход в вершину 4, время входа: 3
Выход из вершины 4, время выхода: 4
Выход из вершины 2, время выхода: 5
Вход в вершину 3, время входа: 6
Выход из вершины 3, время выхода: 7
Выход из вершины 1, время выхода: 8
Вход в вершину 5, время входа: 9
Вход в вершину 6, время входа: 10
Выход из вершины 6, время выхода: 11
Выход из вершины 5, время выхода: 12
----------------------------------------
Итоговые времена входа и выхода:
Вершина 1: вход=1, выход=8
Вершина 2: вход=2, выход=5
Вершина 3: вход=6, выход=7
Вершина 4: вход=3, выход=4
Вершина 5: вход=9, выход=12
Вершина 6: вход=10, выход=11
