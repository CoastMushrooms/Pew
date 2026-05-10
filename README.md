# Pew

A 2D pixel-art shooter written in Java using Swing. Fight through three levels of enemy waves, survive long enough to reach the boss, and take it down before your health runs out.

## Gameplay

- Move your ship around the grid with the arrow keys
- Shoot blasts with spacebar to destroy enemies
- Survive enemy fire and avoid losing all your HP
- Reach level 3 to face the Boss

## Controls

| Key | Action |
|-----|--------|
| ↑ ↓ ← → | Move |
| Space | Shoot |

## How to Run

**Requirements:** Java 11 or newer

```bash
# Compile
javac *.java

# Run
java GameRunner
```

## Project Structure
├── GameRunner.java&emsp;&emsp;        # Entry point<br>
├── GameEngine.java&emsp;&emsp;        # Game loop, input handling (Controller)<br>
├── GameGrid.java&emsp;&emsp;          # Game state grid (Model)<br>
├── GamePanel.java&emsp;&emsp;         # Rendering (View)<br>
├── Element.java&emsp;&emsp;           # Base class for all game objects<br>
├── Example.java&emsp;&emsp;           # Player character<br>
├── Enemy.java&emsp;&emsp;             # Standard enemy<br>
├── Boss.java&emsp;&emsp;              # Level 3 boss<br>
├── Blast.java&emsp;&emsp;             # Player projectile<br>
├── blastEnemy.java&emsp;&emsp;        # Enemy projectile<br>
├── Wall.java&emsp;&emsp;              # Destructible wall obstacle<br>
├── Coin.java&emsp;&emsp;              # Collectible element<br>
├── SpaceShip.java&emsp;&emsp;         # Alternate character template<br>
├── HealthBar.java&emsp;&emsp;         # Health tracking<br>
├── PixelMap.java&emsp;&emsp;          # Shape/sprite data<br>
├── PixelMapFactory.java&emsp;&emsp;   # Builds PixelMaps from string arrays<br>
├── Pixel.java&emsp;&emsp;             # Single colored grid cell<br>
├── InteractionHandler.java#&emsp;&emsp; Collision detection<br>
├── CollisionReactor.java&emsp;&emsp;  # Collision response interface<br>
├── LevelLoader.java&emsp;&emsp;       # Loads levels from .txt files<br>
├── level1.txt&emsp;&emsp;             # Level 1 layout<br>
├── level2.txt&emsp;&emsp;             # Level 2 layout<br>
└── level3.txt&emsp;&emsp;             # Level 3 layout (boss fight)<br>

## Levels

| Level | Description |
|-------|-------------|
| 1 | Open arena, random enemy spawns, walls to hide behind |
| 2 | Tighter layout, enemies spawn faster |
| 3 | No walls — just you and the Boss |

## Win / Lose

- **Game Over** — your HP hits zero
- **You Win** — defeat the Boss on level 3
