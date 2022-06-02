package main

import (
	"fmt"
	"github.com/go-vgo/robotgo"
	"github.com/robfig/cron"
	"time"
)

func main() {
	c := cron.New()
	spec := "*/30 * * * * ?"
	_ = c.AddFunc(spec, func() {
		width, height := robotgo.GetScreenSize()
		originX, originY := robotgo.GetMousePos()
		if originX < 0 || originX > width {
			fmt.Printf("[%s] originX=%d, 置为1\n", getCurrentTime(), originX)
			originX = 1
		}
		if originY < 0 || originY > height {
			fmt.Printf("[%s] originY=%d, 置为1\n", getCurrentTime(), originY)
			originY = 1
		}
		robotgo.Move(originX-1, originY)
		fmt.Printf("[%s] move mouse: x=%d, y=%d\n", getCurrentTime(), originX-1, originY)
		robotgo.Move(originX, originY)
		fmt.Printf("[%s] move mouse[origin]: x=%d, y=%d\n", getCurrentTime(), originX, originY)
	})
	c.Start()
	channel := make(chan int) //创建整型类型通道
	select {
	case <-channel:
		// do nothing.
		//default:
		// 注释default, select语句一直阻塞直到通道接收数据.
	}
}

func getCurrentTime() string {
	return time.Now().Format("2006-01-02 15:04:05")
}
