Вариант 18. Реализуйте DFS для графа с выводом времени входа и выхода для каждой
вершины.

Описание алгоритма
Depth-First Search (DFS) - алгоритм обхода графа, который исследуется вглубь, прежде чем возвращаться к соседним вершинам. Алгоритм с временными метками отслеживает момент входа и выхода для каждой вершины.

Ключевые понятия:
Время входа - момент, когда алгоритм впервые посещает вершину (entry_time[v] = self.time)

Время выхода - момент, когда алгоритм завершает обработку всех потомков вершины (exit_time[v] = self.time)

Рекурсивный обход - используется системный стек вызовов(self.dfs_util(neighbor, visited, entry_time, exit_time))

 Для каждого узла выполняется: вход → обработка потомков → выход(# Вход (строки 17-20)
self.time += 1
entry_time[v] = self.time
visited[v] = True

# Обработка потомков (строки 23-25)
for neighbor in self.graph[v]:
    if not visited[neighbor]:
        self.dfs_util(neighbor, visited, entry_time, exit_time)

# Выход (строки 27-29)
self.time += 1
exit_time[v] = self.time)


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



Временная сложность: O(V + E)
Где:
V - количество вершин (n)

E - количество ребер


Пояснение почему именно O(V + E):
1. Каждая вершина обрабатывается один раз: O(V)
2. Каждое ребро проверяется один раз: O(E)
3. Суммарно: O(V + E)
Σ(время на все вершины) = Σ[O(1) + O(deg(v))] = O(V) + O(Σ deg(v))
Так как Σ deg(v) = 2E (сумма степеней всех вершин равна удвоенному количеству ребер), получаем:
O(V) + O(2E) = O(V + E)


Ответ на вопрос 3. Почему наивная рекурсивная реализация чисел Фибоначчи неэффективна?



Наивная рекурсивная реализация чисел Фибоначчи неэффективна по нескольким ключевым причинам:

1. Экспоненциальная временная сложность
Временная сложность: O(2ⁿ)

Для вычисления F(n) нужно вычислить F(n-1) и F(n-2)

Каждое из этих вычислений, в свою очередь, требует двух рекурсивных вызовов

Количество операций растет экспоненциально

2. Многократные повторные вычисления
Основная проблема - избыточные вычисления одних и тех же значений:

F(5)
├── F(4)
│   ├── F(3)
│   │   ├── F(2)
│   │   │   ├── F(1)
│   │   │   └── F(0)
│   │   └── F(1)
│   └── F(2)
│       ├── F(1)
│       └── F(0)
└── F(3)
    ├── F(2)
    │   ├── F(1)
    │   └── F(0)
    └── F(1)

F(3) вычисляется 2 раза, F(2) - 3 раза, F(1) - 5 раз

Каждое из этих вычислений, в свою очередь, требует двух рекурсивных вызовов

Количество операций растет экспоненциально


3. Сравнение производительности
n	Наивная рекурсия	Динамическое программирование
20	~2²⁰ операций	20 операций
40	~1.1×10¹² операций	40 операций
50	~1.1×10¹⁵ операций	50 операций
Вывод: Наивная рекурсия практически непригодна для n > 40, в то время как эффективные алгоритмы легко справляются с n = 10⁶ и больше.
