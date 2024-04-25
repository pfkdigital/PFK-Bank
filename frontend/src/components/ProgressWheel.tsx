"use client";

import { Circle } from "rc-progress";

interface ProgressWheelProps {
  progress: number;
}

const ProgressWheel = ({ progress }: ProgressWheelProps) => (
  <div className="relative w-20 h-20">
    <Circle
      percent={progress}
      strokeWidth={20}
      strokeColor="#6D28D9"
      trailWidth={10}
    />
    <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex items-center justify-center">
      <span className="text-sm font-semibold">{progress}%</span>
    </div>
  </div>
);

export default ProgressWheel;
