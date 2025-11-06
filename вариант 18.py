from collections import defaultdict

class Graph:
    def __init__(self):
        self.graph = defaultdict(list)
        self.time = 0
    
    def add_edge(self, u, v):
        """Äîáàâëåíèå ðåáðà îò âåðøèíû u ê âåðøèíå v"""
        self.graph[u].append(v)
    
    def dfs_util(self, v, visited, entry_time, exit_time):
        """Âñïîìîãàòåëüíàÿ ôóíêöèÿ äëÿ ðåêóðñèâíîãî DFS"""
        # Óâåëè÷èâàåì âðåìÿ è çàïèñûâàåì âðåìÿ âõîäà
        self.time += 1
        entry_time[v] = self.time
        visited[v] = True
        print(f"Âõîä â âåðøèíó {v}, âðåìÿ âõîäà: {entry_time[v]}")
        
        # Ðåêóðñèâíî ïîñåùàåì âñå ñìåæíûå âåðøèíû
        for neighbor in self.graph[v]:
            if not visited[neighbor]:
                self.dfs_util(neighbor, visited, entry_time, exit_time)
        
        # Óâåëè÷èâàåì âðåìÿ è çàïèñûâàåì âðåìÿ âûõîäà
        self.time += 1
        exit_time[v] = self.time
        print(f"Âûõîä èç âåðøèíû {v}, âðåìÿ âûõîäà: {exit_time[v]}")
    
    def dfs(self, start_vertex=None):
        """Îñíîâíàÿ ôóíêöèÿ DFS"""
        # Èíèöèàëèçàöèÿ ìàññèâîâ
        visited = defaultdict(bool)
        entry_time = {}
        exit_time = {}
        self.time = 0
        
        # Åñëè ñòàðòîâàÿ âåðøèíà íå óêàçàíà, áåðåì ïåðâóþ èç ãðàôà
        if start_vertex is None:
            start_vertex = list(self.graph.keys())[0] if self.graph else 0
        
        print("Íà÷èíàåì îáõîä â ãëóáèíó (DFS):")
        print("-" * 40)
        
        # Çàïóñêàåì DFS èç ñòàðòîâîé âåðøèíû
        if not visited[start_vertex]:
            self.dfs_util(start_vertex, visited, entry_time, exit_time)
        
        # Ïðîâåðÿåì âñå âåðøèíû (äëÿ íåñâÿçíûõ ãðàôîâ)
        for vertex in list(self.graph.keys()):
            if not visited[vertex]:
                self.dfs_util(vertex, visited, entry_time, exit_time)
        
        print("-" * 40)
        print("Èòîãîâûå âðåìåíà âõîäà è âûõîäà:")
        for vertex in sorted(entry_time.keys()):
            print(f"Âåðøèíà {vertex}: âõîä={entry_time[vertex]}, âûõîä={exit_time[vertex]}")
        
        return entry_time, exit_time

# Ïðèìåð èñïîëüçîâàíèÿ
if __name__ == "__main__":
    # Ñîçäàåì ãðàô
    g = Graph()
    
    # Äîáàâëÿåì ðåáðà
    g.add_edge(0, 1)
    g.add_edge(0, 2)
    g.add_edge(1, 2)
    g.add_edge(2, 0)
    g.add_edge(2, 3)
    g.add_edge(3, 3)
    
    # Âûïîëíÿåì DFS
    entry, exit = g.dfs(2)
    
    print("\n" + "="*50)
    
    # Äðóãîé ïðèìåð ñ íåñâÿçíûì ãðàôîì
    print("\nÏðèìåð ñ íåñâÿçíûì ãðàôîì:")
    g2 = Graph()
    g2.add_edge(1, 2)
    g2.add_edge(1, 3)
    g2.add_edge(2, 4)
    g2.add_edge(5, 6)  # Îòäåëüíàÿ êîìïîíåíòà ñâÿçíîñòè
    
    entry2, exit2 = g2.dfs(1)





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

<completed in 45 ms>

