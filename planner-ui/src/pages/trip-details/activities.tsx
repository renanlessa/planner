import { CircleCheck } from "lucide-react";
import { ptBR } from "date-fns/locale";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { api } from "../../lib/axios";
import { format, parseISO } from "date-fns";

interface Activity {
  date: string
  activities: {
    id: string
    title: string
    dateTime: string
  }[]
}

export function Activities() {
  const { tripId } = useParams()
  const [activities, setActivities] = useState<Activity[]>([])

  useEffect(() => {
    api.get(`/trips/${tripId}/activities`)
    .then(response => setActivities(response.data))
  }, [tripId])

  return (
    <div className="space-y-8">
      {activities.map(activity => {
        return (
          <div key={activity.date} className="space-y-2.5">
            <div className="flex gap-2 items-baseline">
              <span className="text-xl text-zinc-300 font-semibold">Dia {format(parseISO(activity.date) , 'd')}</span>
              <span className="text-xs text-zinc-500">{format(parseISO(activity.date), 'EEEE', { locale: ptBR })}</span>
            </div>
            {activity.activities.length > 0 ? (
                activity.activities.map(activityDetail => {
                  return (
                    <div key={activityDetail.id} className="space-y-2.5">
                      <div className="px-4 py-2.5 bg-zinc-900 rounded-xl shadow-shape flex items-center gap-3">
                        <CircleCheck className="size-5 text-lime-300" />
                        <span className="text-zinc-100">{activityDetail.title}</span>
                        <span className="text-zinc-400 text-sm ml-auto">{format(activityDetail.dateTime, 'HH:mm')}h</span>
                      </div>
                    </div>
                  )
                })
            ) : (
              <p className="text-zinc-500 text-sm">Nenhuma atividade cadastrada nessa data.</p>
            )}
          </div>
        )
      })}      
    </div>
  )
}