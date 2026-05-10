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
├── GameRunner.java        # Entry point
├── GameEngine.java        # Game loop, input handling (Controller)
├── GameGrid.java          # Game state grid (Model)
├── GamePanel.java         # Rendering (View)
├── Element.java           # Base class for all game objects
├── Example.java           # Player character
├── Enemy.java             # Standard enemy
├── Boss.java              # Level 3 boss
├── Blast.java             # Player projectile
├── blastEnemy.java        # Enemy projectile
├── Wall.java              # Destructible wall obstacle
├── Coin.java              # Collectible element
├── SpaceShip.java         # Alternate character template
├── HealthBar.java         # Health tracking
├── PixelMap.java          # Shape/sprite data
├── PixelMapFactory.java   # Builds PixelMaps from string arrays
├── Pixel.java             # Single colored grid cell
├── InteractionHandler.java# Collision detection
├── CollisionReactor.java  # Collision response interface
├── LevelLoader.java       # Loads levels from .txt files
├── level1.txt             # Level 1 layout
├── level2.txt             # Level 2 layout
└── level3.txt             # Level 3 layout (boss fight)

## Levels

| Level | Description |
|-------|-------------|
| 1 | Open arena, random enemy spawns, walls to hide behind |
| 2 | Tighter layout, enemies spawn faster |
| 3 | No walls — just you and the Boss |

## Win / Lose

- **Game Over** — your HP hits zero
- **You Win** — defeat the Boss on level 3
