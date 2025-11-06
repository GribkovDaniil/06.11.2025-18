#include <iostream>
#include <vector>
#include <map>
#include <list>
#include <algorithm>

class Graph {
private:
    std::map<int, std::list<int>> graph;
    int time;

    void dfsUtil(int v, std::map<int, bool>& visited, 
                 std::map<int, int>& entry_time, 
                 std::map<int, int>& exit_time) {
        // Увеличиваем время и записываем время входа
        time++;
        entry_time[v] = time;
        visited[v] = true;
        std::cout << "Вход в вершину " << v << ", время входа: " << entry_time[v] << std::endl;
        
        // Рекурсивно посещаем все смежные вершины
        for (int neighbor : graph[v]) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited, entry_time, exit_time);
            }
        }
        
        // Увеличиваем время и записываем время выхода
        time++;
        exit_time[v] = time;
        std::cout << "Выход из вершины " << v << ", время выхода: " << exit_time[v] << std::endl;
    }

public:
    Graph() : time(0) {}
    
    void addEdge(int u, int v) {
        /** Добавление ребра от вершины u к вершине v */
        graph[u].push_back(v);
    }
    
    std::pair<std::map<int, int>, std::map<int, int>> dfs(int start_vertex = -1) {
        /** Основная функция DFS */
        // Инициализация массивов
        std::map<int, bool> visited;
        std::map<int, int> entry_time;
        std::map<int, int> exit_time;
        time = 0;
        
        // Если стартовая вершина не указана, берем первую из графа
        if (start_vertex == -1) {
            if (!graph.empty()) {
                start_vertex = graph.begin()->first;
            } else {
                start_vertex = 0;
            }
        }
        
        std::cout << "Начинаем обход в глубину (DFS):" << std::endl;
        std::cout << "----------------------------------------" << std::endl;
        
        // Запускаем DFS из стартовой вершины
        if (!visited[start_vertex]) {
            dfsUtil(start_vertex, visited, entry_time, exit_time);
        }
        
        // Проверяем все вершины (для несвязных графов)
        for (auto& vertex_pair : graph) {
            int vertex = vertex_pair.first;
            if (!visited[vertex]) {
                dfsUtil(vertex, visited, entry_time, exit_time);
            }
        }
        
        std::cout << "----------------------------------------" << std::endl;
        std::cout << "Итоговые времена входа и выхода:" << std::endl;
        for (auto& entry : entry_time) {
            int vertex = entry.first;
            std::cout << "Вершина " << vertex << ": вход=" << entry_time[vertex] 
                      << ", выход=" << exit_time[vertex] << std::endl;
        }
        
        return {entry_time, exit_time};
    }
};

int main() {
    // Создаем граф
    Graph g;
    
    // Добавляем ребра
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(2, 3);
    g.addEdge(3, 3);
    
    // Выполняем DFS
    auto [entry, exit] = g.dfs(2);
    
    std::cout << "\n==================================================" << std::endl;
    
    // Другой пример с несвязным графом
    std::cout << "\nПример с несвязным графом:" << std::endl;
    Graph g2;
    g2.addEdge(1, 2);
    g2.addEdge(1, 3);
    g2.addEdge(2, 4);
    g2.addEdge(5, 6);  // Отдельная компонента связности
    
    auto [entry2, exit2] = g2.dfs(1);
    
    return 0;
}