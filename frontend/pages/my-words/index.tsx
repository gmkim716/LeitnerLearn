import useLearningStats from "@/hooks/useLeaningStats";
import useUserInfo from "@/hooks/useUserInfo";

export default function MyWordsPage() {
  const user = useUserInfo();
  const userId = user?.userId;
  const { reviewingCards, longTermMemoryCards, learningLevelStats } =
    useLearningStats(userId || 0);

  console.log("reviewingCards", reviewingCards);
  console.log("longTermMemoryCards", longTermMemoryCards);
  console.log("learningLevelStats", learningLevelStats);

  return (
    <>
      <div>level1: {learningLevelStats?.level01Counts} 문제 </div>
      <div>level2: {learningLevelStats?.level02Counts} 문제 </div>
      <div>level3: {learningLevelStats?.level03Counts} 문제 </div>
      <div>level4: {learningLevelStats?.level04Counts} 문제 </div>
    </>
  );
}
