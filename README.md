# java finalProject Huluwa

**程序运行说明:**

1. 运行程序
2. 开始新的对战：按下空格键即可开始，在对战进行的任何时刻按下空格键会开始新的对战，无论对战是否被中断，每次对战记录在项目当前目录下`recordi.txt下`，`i`表示第i次对战；
3. 从记录中回放对战：点击菜单栏`read Record` 的菜单项`open`会打开文件选择器，默认在项目当前目录下，选择`recordi.txt`就会开始回放对战。
4. 关闭窗口，程序结束运行。


**程序结构:**

1. 类、对象：
	* 类`Main`：程序入口所在类，包含默认构造方法、`main`方法和`InitUI`方法，`InitUI`方法中定义新的战场对象并添加菜单栏；
	* 类`File_Chooser`实现接口`ActionListener`，完成文件选择与打开；
	* 类`Creature`：描述任意的生物，它包含生物的图片`image`、运动方向`direction`、对战立场`stand`、是否存活`is_alive`、战斗力`strength`等成员：
		* 继承它的子类型有：
			* `Huluwa_Entity`（葫芦娃）
			* `Scorpion_Entity`（小妖）
			* `Grandpa_Entity`（老爷爷）
			* `Snake_Entity`（蛇精）
			* `LS_Entity`（蝎子精）
		* 类`Creature`实现了接口`Cloneable`用来完成生物对象的浅拷贝。
	* 类`Location`：描述方向，包括横纵坐标`x`，`y`；
	* 类`Player`：描述对战中的玩家，包含玩家位置`location`、玩家生物·creature·和所在战场`myfield`等成员：
		* 本类实现了`Runnable`接口，每个玩家可以作为一个独立的线程；
		* `move`方法计算玩家下一个期待移动的位置，并调用`myfield`的`moveable`方法请求移动，`moveable`用`synchronized`修饰作为临界区，确保对共享资源的互斥访问；
		* 重写的`run`方法在改玩家存活且对战没有结束的情况下调用`move`方法，并定时`sleep`。
	* 类`Formation`：描述玩家阵型，包含阵型起点`border`，玩家集合`players`（用`Vector`描述）等成员：
		* 继承它的子类型有：
			* `Snake_formation` (长蛇阵型)
			* `Goose_formation` (雁行阵型)
			* `Crane_formation` (鹤翼阵型)
			* `Single_formation` (单个玩家)
	* 类`Field`：描述战斗，继承自`JPanel`，重写了`paint`方法：
		* 包含成员：
			* 战场的长`w`宽`h`、战场`field`（用二维数组`Player[][]`描述）；
			* `start`、`is_end`、`is_record`指示战斗开始、结束、回放；
		* 包含内部类：`TAdapter`继承自`KeyAdapter`，重写了其`keyPressed`方法：按下空格键初始化战斗世界并开始对战
		* 包含方法：
			* 构造方法

			

2. 并发
