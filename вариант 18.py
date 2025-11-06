from collections import defaultdict

class Graph:
    def __init__(self):
        self.graph = defaultdict(list)
        self.time = 0
    
    def add_edge(self, u, v):
        """Добавление ребра от вершины u к вершине v"""
        self.graph[u].append(v)
    
    def dfs_util(self, v, visited, entry_time, exit_time):
        """Вспомогательная функция для рекурсивного DFS"""
        # Увеличиваем время и записываем время входа
        self.time += 1
        entry_time[v] = self.time
        visited[v] = True
        print(f"Вход в вершину {v}, время входа: {entry_time[v]}")
        
        # Рекурсивно посещаем все смежные вершины
        for neighbor in self.graph[v]:
            if not visited[neighbor]:
                self.dfs_util(neighbor, visited, entry_time, exit_time)
        
        # Увеличиваем время и записываем время выхода
        self.time += 1
        exit_time[v] = self.time
        print(f"Выход из вершины {v}, время выхода: {exit_time[v]}")
    
    def dfs(self, start_vertex=None):
        """Основная функция DFS"""
        # Инициализация массивов
        visited = defaultdict(bool)
        entry_time = {}
        exit_time = {}
        self.time = 0
        
        # Если стартовая вершина не указана, берем первую из графа
        if start_vertex is None:
            start_vertex = list(self.graph.keys())[0] if self.graph else 0
        
        print("Начинаем обход в глубину (DFS):")
        print("-" * 40)
        
        # Запускаем DFS из стартовой вершины
        if not visited[start_vertex]:
            self.dfs_util(start_vertex, visited, entry_time, exit_time)
        
        # Проверяем все вершины (для несвязных графов)
        for vertex in list(self.graph.keys()):
            if not visited[vertex]:
                self.dfs_util(vertex, visited, entry_time, exit_time)
        
        print("-" * 40)
        print("Итоговые времена входа и выхода:")
        for vertex in sorted(entry_time.keys()):
            print(f"Вершина {vertex}: вход={entry_time[vertex]}, выход={exit_time[vertex]}")
        
        return entry_time, exit_time

# Пример использования
if __name__ == "__main__":
    # Создаем граф
    g = Graph()
    
    # Добавляем ребра
    g.add_edge(0, 1)
    g.add_edge(0, 2)
    g.add_edge(1, 2)
    g.add_edge(2, 0)
    g.add_edge(2, 3)
    g.add_edge(3, 3)
    
    # Выполняем DFS
    entry, exit = g.dfs(2)
    
    print("\n" + "="*50)
    
    # Другой пример с несвязным графом
    print("\nПример с несвязным графом:")
    g2 = Graph()
    g2.add_edge(1, 2)
    g2.add_edge(1, 3)
    g2.add_edge(2, 4)
    g2.add_edge(5, 6)  # Отдельная компонента связности
    
    entry2, exit2 = g2.dfs(1)

