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
├── GameRunner.java        # Entry point<br>
├── GameEngine.java        # Game loop, input handling (Controller)<br>
├── GameGrid.java          # Game state grid (Model)<br>
├── GamePanel.java         # Rendering (View)<br>
├── Element.java           # Base class for all game objects<br>
├── Example.java           # Player character<br>
├── Enemy.java             # Standard enemy<br>
├── Boss.java              # Level 3 boss<br>
├── Blast.java             # Player projectile<br>
├── blastEnemy.java        # Enemy projectile<br>
├── Wall.java              # Destructible wall obstacle<br>
├── Coin.java              # Collectible element<br>
├── SpaceShip.java         # Alternate character template<br>
├── HealthBar.java         # Health tracking<br>
├── PixelMap.java          # Shape/sprite data<br>
├── PixelMapFactory.java   # Builds PixelMaps from string arrays<br>
├── Pixel.java             # Single colored grid cell<br>
├── InteractionHandler.java# Collision detection<br>
├── CollisionReactor.java  # Collision response interface<br>
├── LevelLoader.java       # Loads levels from .txt files<br>
├── level1.txt             # Level 1 layout<br>
├── level2.txt             # Level 2 layout<br>
└── level3.txt             # Level 3 layout (boss fight)<br>

## Levels

| Level | Description |
|-------|-------------|
| 1 | Open arena, random enemy spawns, walls to hide behind |
| 2 | Tighter layout, enemies spawn faster |
| 3 | No walls — just you and the Boss |

## Win / Lose

- **Game Over** — your HP hits zero
- **You Win** — defeat the Boss on level 3
